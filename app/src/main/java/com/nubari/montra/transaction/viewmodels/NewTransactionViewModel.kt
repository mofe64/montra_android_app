package com.nubari.montra.transaction.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.nubari.montra.R
import com.nubari.montra.budget.events.BudgetProcessEvent
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.data.local.models.enums.TransactionFrequency
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.usecases.budget.BudgetUseCases
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.general.util.Constants.BUDGET_NOTIFICATION_CHANNEL_ID
import com.nubari.montra.general.util.InputType
import com.nubari.montra.general.util.Util
import com.nubari.montra.internal.workers.NotificationWorker
import com.nubari.montra.preferences
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.transaction.events.TransactionProcessEvent
import com.nubari.montra.transaction.state.TransactionFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NewTransactionViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val transactionUseCases: TransactionUseCases,
    private val budgetUseCases: BudgetUseCases
) : ViewModel() {
    private val tag = "New_Transaction_ViewModel"
    private val _state = mutableStateOf(
        TransactionFormState(
            formValid = true,
        )
    )
    val state: State<TransactionFormState> = _state

    private val _eventFlow = MutableSharedFlow<TransactionProcessEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllCategories()
    }

    private fun getAllCategories() {
        categoryUseCases.getAllCategories()
            .onEach {
                _state.value = state.value.copy(
                    categories = it
                )
            }.launchIn(viewModelScope)
    }


    fun createEvent(event: TransactionFormEvent) {
        onEvent(event = event)
    }

    @SuppressLint("TimberArgCount", "TimberTagLength")
    private fun onEvent(event: TransactionFormEvent) {
        when (event) {
            is TransactionFormEvent.ChangedCategory -> {
                _state.value = state.value.copy(
                    category = state.value.category.copy(
                        text = event.category
                    )
                )
            }
            is TransactionFormEvent.AddedAttachment -> {
                _state.value = state.value.copy(
                    attachment = state.value.attachment.copy(
                        text = event.attachment
                    )
                )
            }
            is TransactionFormEvent.ChangedAccount -> {
                _state.value = state.value.copy(
                    account = state.value.account.copy(
                        text = event.accountId
                    )
                )
            }
            is TransactionFormEvent.EnteredDescription -> {
                _state.value = state.value.copy(
                    description = state.value.description.copy(
                        text = event.value
                    )
                )
            }
            is TransactionFormEvent.ToggledRepeatTransaction -> {
                _state.value = state.value.copy(
                    isRecurring = !state.value.isRecurring
                )
            }
            is TransactionFormEvent.FocusChange -> {
                when (event.focusFieldName) {
                    "description" -> {
                        val validity =
                            Util.validateInput(state.value.description.text, InputType.TEXT)
                        _state.value = state.value.copy(
                            description = state.value.description.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                    "amount" -> {
                        val cleanedAmount = Util.cleanNumberInput(state.value.amount.text)
                        val validity =
                            Util.validateInput(cleanedAmount, InputType.NUMBER)
                        _state.value = state.value.copy(
                            amount = state.value.amount.copy(
                                isValid = validity.first,
                                errorMessage = validity.second
                            ),
                            formValid = validity.first
                        )
                    }
                    "frequency" -> {
                        val selectedFrequency = state.value.recurringFrequency.text
                        val validFrequencyOptions = TransactionFrequency.values().map {
                            it.name
                        }
                        if (!validFrequencyOptions.contains(selectedFrequency)) {
                            _state.value = state.value.copy(
                                formValid = false,
                                recurringFrequency = state.value.recurringFrequency.copy(
                                    isValid = false,
                                    errorMessage = "Please select a valid frequency"
                                )
                            )

                        }
                    }
                }
            }
            is TransactionFormEvent.EnteredAmount -> {
                _state.value = state.value.copy(
                    amount = state.value.amount.copy(
                        text = event.amount
                    )
                )
            }
            is TransactionFormEvent.SetRecurringFrequency -> {
                _state.value = state.value.copy(
                    recurringFrequency = state.value.recurringFrequency.copy(
                        text = event.frequency
                    )
                )
            }
            is TransactionFormEvent.CreateTransaction -> {

                val allValuesValid = validateForm()
                if (!allValuesValid) {
                    return
                }
                val category: Category
                try {
                    category = state.value.categories.first {
                        it.name == state.value.category.text
                    }
                } catch (e: NoSuchElementException) {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            TransactionProcessEvent.TransactionCreationFail(
                                "Please select a valid category"
                            )
                        )
                    }
                    return


                }

                val frequencyString = event.txFrequency ?: ""
                val frequency = if (frequencyString.isNotEmpty()) {
                    TransactionFrequency.valueOf(frequencyString)
                } else {
                    null
                }
                _state.value = state.value.copy(
                    isProcessing = true
                )

                val amount = BigDecimal(Util.cleanNumberInput(event.amount))
                viewModelScope.launch {
                    val tx = Transaction(
                        id = UUID.randomUUID().toString(),
                        accountId = preferences.activeAccountId,
                        categoryId = category.id,
                        date = Date(),
                        type = event.type,
                        amount = amount,
                        isRecurring = event.repeat,
                        subscriptionId = null,
                        name = event.txName,
                        description = event.txDescription,
                        attachmentLocal = null,
                        attachmentRemote = null,
                        frequency = frequency,
                        categoryName = event.categoryName,
                    )
                    Timber.tag(tag).i("new transaction", tx)
                    transactionUseCases.createTransaction(tx)


                    if (event.type == TransactionType.EXPENSE) {
                        Timber.tag(tag).i("Tx type is expense")
                        Timber.tag(tag).i("Looking for matching category budget")
                        // Get user category budget
                        val categoryName = event.categoryName
                        val budget = budgetUseCases.getBudgetForACategory(
                            categoryName = categoryName
                        )

                        budget?.let { budgetToUpdate ->
                            Timber.tag(tag).i("Matching budget found", budgetToUpdate)
                            val updatedSpend = budgetToUpdate.spend.add(amount)
                            val hasExceededLimit = budgetToUpdate.limit < updatedSpend
                            budgetUseCases.updateBudgetSpend(
                                budgetId = budgetToUpdate.id,
                                exceeded = hasExceededLimit,
                                updatedSpend
                            )
                            Timber.tag(tag).i("Budget updated")
                            if (hasExceededLimit) {
                                Timber.tag(tag)
                                    .i("Budget exceeded limit emitting budget exceeded event")
                                _eventFlow.emit(
                                    TransactionProcessEvent.BudgetExceeded(
                                        budget = budgetToUpdate
                                    )
                                )
                            }


                        }
                    }

                    _state.value = state.value.copy(
                        isProcessing = false
                    )
                    _eventFlow.emit(
                        TransactionProcessEvent.TransactionCreationSuccess
                    )

                }
            }

        }
    }

    private fun validateForm(): Boolean {
        var inputValid = true
        val selectedCategory = state.value.category.text
        val validCategories = state.value.categories
        val categoryMatch = validCategories.filter {
            it.name == selectedCategory
        }
        if (categoryMatch.isEmpty()) {
            inputValid = false
            _state.value = state.value.copy(
                formValid = false,
                category = state.value.category.copy(
                    isValid = false,
                    errorMessage = "Please select a valid category"
                )
            )
        }
        val descriptionValidity = Util.validateInput(
            state.value.description.text, InputType.TEXT
        )
        if (!descriptionValidity.first) {
            inputValid = false
            _state.value = state.value.copy(
                formValid = false,
                description = state.value.description.copy(
                    isValid = false,
                    errorMessage = descriptionValidity.second
                )
            )
        }
        val cleanedAmount = Util.cleanNumberInput(state.value.amount.text)
        val amountValidity = Util.validateInput(cleanedAmount, InputType.NUMBER)
        if (!amountValidity.first) {
            inputValid = false
            _state.value = state.value.copy(
                formValid = false,
                amount = state.value.amount.copy(
                    isValid = false,
                    errorMessage = amountValidity.second
                )
            )
        }

        val isRecurring = state.value.isRecurring
        if (isRecurring) {
            val selectedFrequency = state.value.recurringFrequency.text
            val validFrequencyOptions = TransactionFrequency.values().map {
                it.name
            }
            if (!validFrequencyOptions.contains(selectedFrequency)) {
                inputValid = false
                _state.value = state.value.copy(
                    formValid = false,
                    recurringFrequency = state.value.recurringFrequency.copy(
                        isValid = false,
                        errorMessage = "Please select a valid frequency"
                    )
                )

            }
        }
        return inputValid
    }
}