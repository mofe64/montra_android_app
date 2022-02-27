package com.nubari.montra.transaction.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.preferences
import com.nubari.montra.transaction.events.TransactionScreenEvent
import com.nubari.montra.transaction.state.TransactionsState
import com.nubari.montra.transaction.util.TransactionSortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        TransactionsState(
            filterBy = null,
        )
    )
    val state: State<TransactionsState> = _state
    private var loadTransactionsJob: Job? = null

    init {
        loadTransactions()
    }

    fun createEvent(event: TransactionScreenEvent) {
        onEvent(event = event)
    }

    private fun onEvent(event: TransactionScreenEvent) {
        when (event) {
            is TransactionScreenEvent.Filter -> {
                _state.value = state.value.copy(
                    filterBy = event.filterBy
                )
                loadTransactions()
            }
            is TransactionScreenEvent.Sort -> {
                _state.value = state.value.copy(
                    sortBy = event.sortBy
                )
                loadTransactions()
            }
            is TransactionScreenEvent.Reset -> {
                _state.value = state.value.copy(
                    filterBy = null,
                    sortBy = TransactionSortBy.DATE_DESC
                )
                loadTransactions()
            }
        }
    }

    private fun loadTransactions() {
        //TODO add error handling in case active account id is empty
        val activeAccountId = preferences.activeAccountId
        loadTransactionsJob?.cancel()
        loadTransactionsJob = transactionUseCases
            .getTransactionsForAccount(
                filterBy = state.value.filterBy,
                sortBy = state.value.sortBy,
                accountId = activeAccountId
            )
            .onEach {
                _state.value = state.value.copy(
                    transactions = it
                )
            }
            .launchIn(viewModelScope)
    }
}