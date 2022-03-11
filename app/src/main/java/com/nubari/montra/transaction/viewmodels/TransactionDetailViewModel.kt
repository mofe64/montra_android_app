package com.nubari.montra.transaction.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.transaction.events.TransactionDetailEvent
import com.nubari.montra.transaction.events.TransactionProcessEvent
import com.nubari.montra.transaction.state.TransactionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(
        TransactionDetailState(
            transaction = null
        )
    )

    val state = _state

    private val _eventFlow = MutableSharedFlow<TransactionProcessEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("txId")?.let {
            if (it.isNotEmpty()) {
                viewModelScope.launch {
                    transactionUseCases
                        .getTransactionById(id = it)
                        ?.also { tx ->
                            _state.value = state.value.copy(
                                transaction = tx
                            )
                        }
                }
            }
        }
    }

    fun createEvent(event: TransactionDetailEvent) {
        onEvent(event)
    }

    private fun onEvent(event: TransactionDetailEvent) {
        when (event) {
            is TransactionDetailEvent.DeleteTransaction -> {
                viewModelScope.launch {
                    transactionUseCases.deleteTransaction(event.tx)
                    _eventFlow.emit(
                        TransactionProcessEvent.TransactionDeleteSuccess
                    )
                }

            }

        }
    }
}