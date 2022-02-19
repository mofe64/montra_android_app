package com.nubari.montra.transaction.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
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
                val categoryNameIdPair = mutableListOf<Pair<String, String>>()
                it.forEach { category ->
                    val pair = Pair(category.id, category.name)
                    categoryNameIdPair.add(pair)
                }
                _state.value = state.value.copy(
                    categories = categoryNameIdPair
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
            is TransactionFormEvent.FocusChange -> {}
            is TransactionFormEvent.EnteredAmount -> {
                _state.value = state.value.copy(
                    amount = state.value.amount.copy(
                        text = event.amount
                    )
                )
            }
            is TransactionFormEvent.CreateTransaction -> {
                _state.value = state.value.copy(
                    isProcessing = true
                )
                var categoryId = ""
                state.value.categories?.forEach {
                    if (it.second == event.categoryName) {
                        categoryId = it.first
                    }
                }

                viewModelScope.launch {
                    val tx = Transaction(
                        id = UUID.randomUUID().toString(),
                        accountId = preferences.activeAccountId,
                        categoryId = categoryId,
                        date = Date(),
                        type = event.type,
                        amount = BigDecimal(event.amount),
                        isRecurring = false,
                        subscriptionId = null,
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
}