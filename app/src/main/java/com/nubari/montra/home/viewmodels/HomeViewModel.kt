package com.nubari.montra.home.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.data.local.models.AccountTransactions
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.usecases.account.AccountUseCases
import com.nubari.montra.home.events.HomeEvent
import com.nubari.montra.home.state.HomeState
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountUseCases: AccountUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        HomeState(
            account = null,
            accountTransactions = null,
            recentTransactions = null,
            monthsTransactions = null,
            spendingData = null,
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

    //todo refactor this to use the get account tx call to retrieve both account and tx together
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
            val accountTransactions =
                accountUseCases.getAccountTransactions(accountId = activeAccountId)

            accountTransactions?.let {
                Log.i("account-tx", it.toString())
                val recentTx = getRecentTransactions(it)
                val monthTx = getMonthTransactions(it)
                val incomeAndExpense = calculateIncomeAndExpenses(monthTx)
                val totalIncome = incomeAndExpense.first
                val totalExpense = incomeAndExpense.second
                val data = generateSpendingData(monthTx)
                _state.value = state.value.copy(
                    income = totalIncome.toPlainString(),
                    expenses = totalExpense.toPlainString(),
                    accountTransactions = it,
                    monthsTransactions = monthTx,
                    recentTransactions = recentTx,
                    spendingData = data
                )
            }
        }
    }

    private fun calculateIncomeAndExpenses(list: List<Transaction>): Pair<BigDecimal, BigDecimal> {
        var totalIncome = BigDecimal.ZERO
        var totalExpense = BigDecimal.ZERO
        list.forEach {
            if (it.type == TransactionType.EXPENSE) {
                totalExpense = totalExpense.add(it.amount)
            } else {
                totalIncome = totalIncome.add(it.amount)
            }
        }
        return Pair(totalIncome, totalExpense)
    }

    private fun getMonthTransactions(accountTx: AccountTransactions): List<Transaction> {
        val currentYear = Calendar.getInstance(Locale.getDefault()).get(Calendar.YEAR)
        Log.i("month-tx", accountTx.transactions.toString())
        val x = accountTx.transactions.filter {
            it.date.month == state.value.currentMonth
        }
        Log.i("month", state.value.currentMonth.toString())
        Log.i("spending-data-init-test", x.toString())
        return x

    }



    private fun getRecentTransactions(accountTx: AccountTransactions): List<Transaction> {
        val sortedTx = accountTx.transactions.sortedByDescending { it.date }
        return if (sortedTx.size >= 11) {
            sortedTx.subList(0, 11)
        } else {
            sortedTx.subList(0, sortedTx.size)
        }
    }

    fun createEvent(event: HomeEvent) {
        onEvent(event)
    }

    private fun generateSpendingData(tx: List<Transaction>): List<Double> {
        val spendingData = mutableListOf<Double>()
        val sortedTx = tx.sortedBy { it.date }
        sortedTx.forEach {
            if (it.type == TransactionType.EXPENSE) {
                spendingData.add(it.amount.toDouble())
            }
        }
        Log.i("spending-data", spendingData.toString())
        return spendingData.toList()
    }

    private fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ChangeMonth -> {
                _state.value = state.value.copy(
                    currentMonth = event.newMonth,
                )
                state.value.accountTransactions?.let {
                    val monthTx = getMonthTransactions(it)
                    val incomeAndExpenses = calculateIncomeAndExpenses(monthTx)
                    val totalIncome = incomeAndExpenses.first
                    val totalExpense = incomeAndExpenses.second
                    _state.value = state.value.copy(
                        monthsTransactions = monthTx,
                        spendingData = generateSpendingData(monthTx),
                        income = totalIncome.toPlainString(),
                        expenses = totalExpense.toPlainString(),
                    )
                }
            }
        }
    }

}

