package com.nubari.montra.home.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.usecases.account.AccountUseCases
import com.nubari.montra.home.state.HomeState
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        HomeState(
            account = null,
            accountTransactions = null
        )
    )
    val state: State<HomeState> = _state

    init {
        setup()
    }

    private fun setup() {
        setupAccount()
        setupTransactions()
    }

    private fun setupAccount() {
        val activeAccountId = preferences.activeAccountId
        Log.i("account", activeAccountId)
        if (activeAccountId.isEmpty()) {
            Log.i("account", "id empty")
            accountUseCases.getAllAccounts()
                .onEach {
                    Log.i("account", "get all run ${it[0]}")
                    _state.value = state.value.copy(
                        account = it[0]
                    )
                    preferences.activeAccountId = it[0].id
                }.launchIn(viewModelScope)
        } else {
            Log.i("account", "id found")
            viewModelScope.launch {
                val activeAccount = accountUseCases.getAccount(activeAccountId)
                Log.i("account", activeAccount.toString())
                _state.value = state.value.copy(
                    account = activeAccount
                )
            }
        }
    }

    private fun setupTransactions() {
        val activeAccountId = preferences.activeAccountId
        viewModelScope.launch {
            var totalIncome = BigDecimal.ZERO
            var totalExpense = BigDecimal.ZERO
            val accountTransactions =
                accountUseCases.getAccountTransactions(accountId = activeAccountId)
            accountTransactions?.let {
                Log.i("account-tx", it.toString())
                it.transactions.forEach { tx ->
                    if (tx.type == TransactionType.EXPENSE) {
                        totalExpense = totalExpense.add(tx.amount)
                    } else {
                        totalIncome = totalIncome.add(tx.amount)
                    }
                }
                _state.value = state.value.copy(
                    income = totalIncome.toPlainString(),
                    expenses = totalExpense.toPlainString(),
                    accountTransactions = it
                )
            }

        }
    }

}

