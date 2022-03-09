package com.nubari.montra.transaction.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.data.models.CategoryBreakdown
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.preferences
import com.nubari.montra.transaction.events.TransactionReportEvent
import com.nubari.montra.transaction.state.TransactionReportState
import com.nubari.montra.transaction.util.TransactionSortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TransactionReportViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        TransactionReportState()
    )
    val state: State<TransactionReportState> = _state

    // Todo move quotes to db or api call
    private val quotes = listOf(
        Pair("Problem e no dey finish, make you try dey enjoy", "1da Banton"),
        Pair("Chop life oh, make life no chop you", "Unknown"),
    )

    init {
        setUpStats()
    }


    @SuppressLint("LongLogTag")
    private fun setUpStats() {
        val accountId = preferences.activeAccountId
        if (accountId.isEmpty()) {
            return
        }
        val dateRange = getDateRange()
        var totalMonthExpenses = BigDecimal.ZERO
        var totalMonthIncome = BigDecimal.ZERO
        var biggestSpendCategory = ""
        var biggestIncomeCategory = ""
        var biggestSpendAmount = BigDecimal.ZERO
        var biggestIncomeAmount = BigDecimal.ZERO
        val incomeList: MutableList<Transaction> = mutableListOf()
        val expenseList: MutableList<Transaction> = mutableListOf()

        Log.i("Transaction-Report-Preview-ViewModel", "before on each")
        transactionUseCases.getTransactionsForAccountWithinDateRange(
            accountId = accountId,
            startDate = dateRange.first,
            endDate = dateRange.second,
            filterBy = null,
            sortBy = TransactionSortBy.DATE_DESC
        ).onEach { transactions ->
            Log.i("Transaction-Report-Preview-ViewModel", transactions.toString())
            transactions.forEach {
                if (it.type == TransactionType.EXPENSE) {
                    expenseList.add(it)
                    totalMonthExpenses = totalMonthExpenses.add(it.amount)
                    if (it.amount > biggestSpendAmount) {
                        biggestSpendAmount = it.amount
                        biggestSpendCategory = it.categoryName
                    }
                }
                if (it.type == TransactionType.INCOME) {
                    incomeList.add(it)
                    totalMonthIncome = totalMonthIncome.add(it.amount)
                    if (it.amount > biggestIncomeAmount) {
                        biggestIncomeAmount = it.amount
                        biggestIncomeCategory = it.categoryName
                    }
                }
            }

            _state.value = state.value.copy(
                monthExpenses = totalMonthExpenses.toPlainString(),
                monthIncome = totalMonthIncome.toPlainString(),
                biggestSpendAmount = biggestSpendAmount.toPlainString(),
                biggestIncomeAmount = biggestIncomeAmount.toPlainString(),
                biggestSpendCategory = biggestSpendCategory,
                biggestIncomeCategory = biggestIncomeCategory,
                // Todo some logic to determine which quotes to show, based off income/expense level
                randomQuote = quotes.random(),
                incomeSpendingData = generateSpendingData(incomeList, TransactionType.INCOME),
                expenses = expenseList,
                income = incomeList,
                categoryBreakDown = generateCategoryBreakDown(
                    tx = transactions,
                    totalIncome = totalMonthIncome,
                    totalExpense = totalMonthExpenses
                ),
                expenseSpendingData = generateSpendingData(expenseList, TransactionType.EXPENSE)
            )
        }.launchIn(viewModelScope)

    }

    @SuppressLint("LongLogTag")
    private fun getDateRange(): Pair<Long, Long> {
        val currentMonth = Calendar.getInstance(Locale.getDefault()).get(Calendar.MONTH)
        val currentYear = Calendar.getInstance(Locale.getDefault()).get(Calendar.YEAR)
        val thirtyDayMonths = listOf(8, 10, 5, 3)
        val monthStartAndEndDays = if (currentMonth in thirtyDayMonths) {
            Pair(1, 30)
        } else if (currentMonth == 1) {
            if (currentYear % 4 == 0) {
                Pair(1, 29)
            } else {
                Pair(1, 28)
            }
        } else {
            Pair(1, 31)
        }
        val startDateInstance = Calendar.getInstance()
        val endDateInstance = Calendar.getInstance()

        startDateInstance.set(currentYear, currentMonth, monthStartAndEndDays.first)
        endDateInstance.set(currentYear, currentMonth, monthStartAndEndDays.second)

        val startDate = startDateInstance.time
        val endDate = endDateInstance.time
        Log.i("Transaction-Report-Preview-ViewModel", startDate.toString())
        Log.i("Transaction-Report-Preview-ViewModel", endDate.toString())

        return Pair(startDate.time, endDate.time)
    }

    private fun generateSpendingData(tx: List<Transaction>, spendingCategory: TransactionType): List<Double> {
        val spendingData = mutableListOf<Double>()
        val sortedTx = tx.sortedBy { it.date }
        sortedTx.forEach {
            if (it.type == spendingCategory) {
                spendingData.add(it.amount.toDouble())
            }
        }
        Log.i("spending-data", spendingData.toString())
        return spendingData.toList()
    }

    private fun generateCategoryBreakDown(
        tx: List<Transaction>,
        totalIncome: BigDecimal,
        totalExpense: BigDecimal
    ): List<CategoryBreakdown> {
        val categoryBreakdownList: MutableList<CategoryBreakdown> = mutableListOf()
        categoryUseCases.getAllCategories().onEach {
            it.forEach { category ->

                // break down for income
                val categoryBreakDownIncome = CategoryBreakdown()
                categoryBreakDownIncome.categoryName = category.name
                categoryBreakDownIncome.txType = TransactionType.INCOME

                // break down for expenses
                val categoryBreakDownExpense = CategoryBreakdown()
                categoryBreakDownExpense.categoryName = category.name
                categoryBreakDownExpense.txType = TransactionType.EXPENSE

                tx.forEach { tx ->
                    if (tx.categoryName == category.name) {
                        if (tx.type == TransactionType.INCOME) {
                            categoryBreakDownIncome.categoryAmount =
                                categoryBreakDownIncome.categoryAmount.add(tx.amount)
                        } else {
                            categoryBreakDownExpense.categoryAmount =
                                categoryBreakDownExpense.categoryAmount.add(tx.amount)
                        }
                    }
                }
                val incomePercentage =
                    categoryBreakDownIncome.categoryAmount.divide(BigDecimal.valueOf(100L))
                        .multiply(totalIncome)
                val expensePercentage =
                    categoryBreakDownExpense.categoryAmount.divide(BigDecimal.valueOf(100L))
                        .multiply(totalExpense)
                categoryBreakDownIncome.categoryPercentage = incomePercentage.toFloat()
                categoryBreakDownExpense.categoryPercentage = expensePercentage.toFloat()
                categoryBreakdownList.add(categoryBreakDownIncome)
                categoryBreakdownList.add(categoryBreakDownExpense)
            }
        }.launchIn(viewModelScope)
        return categoryBreakdownList
    }

    fun createEvent(event: TransactionReportEvent) {
        onEvent(event = event)
    }

    private fun onEvent(event: TransactionReportEvent) {
        when (event) {
            is TransactionReportEvent.SwitchedActiveTab -> {
                _state.value = state.value.copy(
                    activeTab = event.newTab
                )
            }
            is TransactionReportEvent.SwitchedActiveTabView -> {
                _state.value = state.value.copy(
                    activeTabView = event.newView
                )
            }
        }
    }
}