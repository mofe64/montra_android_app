package com.nubari.montra.transaction.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionFrequency
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.general.util.InputType
import com.nubari.montra.general.util.Util
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
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewTransactionViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        TransactionFormState(
            formValid = true,
            categories = null,
        )
    )
    val state: State<TransactionFormState> = _state

    private val _eventFlow = MutableSharedFlow<TransactionProcessEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        categoryUseCases.getAllCategories()
            .onEach {
                val categoryNameIdMap = mutableMapOf<String, String>()
                it.forEach { category ->
                    categoryNameIdMap[category.name] = category.id
                }
                _state.value = state.value.copy(
                    categories = categoryNameIdMap
                )
            }.launchIn(viewModelScope)
    }

    fun createEvent(event: TransactionFormEvent) {
        onEvent(event = event)
    }

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
                    "frequency"-> {
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
                val categoryId = state.value.categories?.get(
                    event.categoryName
                ) ?: ""
                if (categoryId.isEmpty()) {
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

                viewModelScope.launch {
                    val tx = Transaction(
                        id = UUID.randomUUID().toString(),
                        accountId = preferences.activeAccountId,
                        categoryId = categoryId,
                        date = Date(),
                        type = event.type,
                        amount = BigDecimal(Util.cleanNumberInput(event.amount)),
                        isRecurring = event.repeat,
                        subscriptionId = null,
                        name = event.txName,
                        description = event.txDescription,
                        attachmentLocal = null,
                        attachmentRemote = null,
                        frequency = frequency
                    )
                    Log.i("account-tx", tx.toString())
                    transactionUseCases.createTransaction(tx)
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
        val validCategoryOptions = state.value.categories?.keys ?: emptySet()
        if (validCategoryOptions.isNotEmpty() && !validCategoryOptions.contains(selectedCategory)) {
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
        val cleanedAmount = Util.cleanNumberInput(state.value.amount.text);
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