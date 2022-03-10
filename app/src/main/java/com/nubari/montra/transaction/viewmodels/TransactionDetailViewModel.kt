package com.nubari.montra.transaction.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.transaction.state.TransactionDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
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
}