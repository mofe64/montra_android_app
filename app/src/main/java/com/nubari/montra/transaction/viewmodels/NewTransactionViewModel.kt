package com.nubari.montra.transaction.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nubari.montra.transaction.events.TransactionFormEvent
import com.nubari.montra.transaction.state.TransactionFormState
import javax.inject.Inject

class NewTransactionViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(
        TransactionFormState(
            formValid = true
        )
    )
    val state: State<TransactionFormState> = _state

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
        }
    }
}