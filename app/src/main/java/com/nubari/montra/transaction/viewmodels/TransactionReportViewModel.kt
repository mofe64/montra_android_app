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
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.preferences
import com.nubari.montra.transaction.events.TransactionReportEvent
import com.nubari.montra.transaction.state.TransactionReportState
import com.nubari.montra.transaction.util.TransactionSortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

/*TODO rethink initialization of this view model
* Current implementation is to initialize at nav host level
* because it is shared with both the preview and transaction report screens,
* This impl leads to the view model being initialized at startup,
* We want to restrict the initialization to only when it is needed,
* because there are some heavy processing ops occurring:
* Maybe late-init ???
* */
private const val tag = "Transaction-Report-Preview-ViewModel"
@SuppressLint("LongLogTag")
@HiltViewModel
class TransactionReportViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases,
) : ViewModel() {
    private val _state = mutableStateOf(
        TransactionReportState()
    )
    val state: State<TransactionReportState> = _state
    private var loadDataJob: Job? = null

    // Todo move quotes to db or api call
    private val quotes = listOf(
        Pair("Problem e no dey finish, make you try dey enjoy", "1da Banton"),
        Pair("Chop life oh, make life no chop you", "Unknown"),
    )

    init {
        loadScreenData()
    }



    private fun loadScreenData() {
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

        loadDataJob = transactionUseCases.getTransactionsForAccountWithinDateRange(
            accountId = accountId,
            startDate = dateRange.first,
            endDate = dateRange.second,
            filterBy = null,
            sortBy = TransactionSortBy.AMOUNT_DESC,
        ).onEach { transactions ->
            Log.i(tag, transactions.toString())
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
            val categoryData = generateCategoryBreakdown(
                tx = transactions,
                totalIncome = totalMonthIncome,
                totalExpense = totalMonthExpenses
            )
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
                categoryBreakDown = categoryData,
                expenseSpendingData = generateSpendingData(expenseList, TransactionType.EXPENSE),
                categoryIncomePieChartData = generateCategoryPieChartData(categoryData, TransactionType.INCOME),
                categoryExpensePieChartData = generateCategoryPieChartData(categoryData, TransactionType.EXPENSE),
            )
        }.launchIn(viewModelScope)

    }

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
        Log.i(tag, startDate.toString())
        Log.i(tag, endDate.toString())

        return Pair(startDate.time, endDate.time)
    }

    private fun generateSpendingData(
        tx: List<Transaction>,
        spendingCategory: TransactionType
    ): List<Double> {
        val spendingData = mutableListOf<Double>()
        val sortedTx = tx.sortedBy { it.date }
        sortedTx.forEach {
            if (it.type == spendingCategory) {
                spendingData.add(it.amount.toDouble())
            }
        }
        return spendingData.toList()
    }

    private fun generateCategoryPieChartData(breakDowns: List<CategoryBreakdown>, type: TransactionType): List<Float> {
        val data = mutableListOf<Float>()
        breakDowns.forEach {
            if (it.txType == type){
                data.add(it.categoryAmount.toFloat())
            }

        }
        return data
    }


    private fun generateCategoryBreakdown(
        tx: List<Transaction>,
        totalIncome: BigDecimal,
        totalExpense: BigDecimal
    ): List<CategoryBreakdown> {
        val categories = mutableListOf<String>()
        val breakdownList = mutableListOf<CategoryBreakdown>()
        tx.forEach {
            val categoryDesignation = it.categoryName + "-" +it.type
            if (!categories.contains(categoryDesignation)) {
                Log.i("$tag-debug", "not found adding $categoryDesignation")
                categories.add(categoryDesignation)
                if (it.type == TransactionType.INCOME) {
                    val incomeBreakdown = CategoryBreakdown(
                        categoryName = it.categoryName,
                        categoryAmount = it.amount,
                        txType = TransactionType.INCOME
                    )
                    Log.i("ttt", "about to add ${it.categoryName} for type INCOME line 186")
                    breakdownList.add(incomeBreakdown)
                } else {
                    val expenseBreakdown = CategoryBreakdown(
                        categoryName = it.categoryName,
                        categoryAmount = it.amount,
                        txType = TransactionType.EXPENSE
                    )
                    breakdownList.add(expenseBreakdown)
                }
            } else {
                val breakdown = if (it.type == TransactionType.INCOME) {
                    Log.i("debug", it.categoryName)
                    Log.i("debug", it.toString())
                    Log.i("debug", breakdownList.toString())
                    breakdownList.first { bd -> bd.categoryName == it.categoryName && bd.txType == TransactionType.INCOME }
                } else {
                    breakdownList.first { bd -> bd.categoryName == it.categoryName && bd.txType == TransactionType.EXPENSE }
                }

                val itemIndex = breakdownList.indexOf(breakdown)
                breakdown.categoryAmount = breakdown.categoryAmount.add(it.amount)
                breakdownList[itemIndex] = breakdown

            }
        }

        breakdownList.forEach {
            val hundred = BigDecimal.valueOf(100L)
            if (it.txType == TransactionType.EXPENSE) {
                Log.i(
                    "$tag-category amount for ${it.categoryName}",
                    it.categoryAmount.toPlainString()
                )
                Log.i("$tag-total expense", totalExpense.toPlainString())
                val percentageValue =
                    it.categoryAmount.divide(totalExpense, MathContext(3, RoundingMode.HALF_UP))
                        .multiply(hundred).toFloat()
                Log.i("$tag-percentage value for ${it.categoryName}", percentageValue.toString())
                it.categoryPercentage = percentageValue

            } else {
                it.categoryPercentage =
                    it.categoryAmount.divide(totalIncome, MathContext(3, RoundingMode.HALF_UP))
                        .multiply(hundred)
                        .toFloat()
            }

        }
        Log.i(
            "$tag-breakdown-list",
            breakdownList.filter { it.txType == TransactionType.EXPENSE }.toString()
        )

        return breakdownList

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
            is TransactionReportEvent.ChangeActiveGraph -> {
                _state.value = state.value.copy(
                    activeGraph = event.newGraph
                )
            }
            is TransactionReportEvent.ChangeSortDirection -> {
                _state.value = state.value.copy(
                    sortDir = event.newDirection
                )
                loadTransactions()
            }
        }
    }

    private fun loadTransactions() {
        val sortBy: TransactionSortBy = when (state.value.sortDir) {
            "asc" -> TransactionSortBy.AMOUNT_ASC
            "desc" -> TransactionSortBy.AMOUNT_DESC
            else -> {
                return
            }
        }
        val dateRange = getDateRange()
        val accountId = preferences.activeAccountId
        if (accountId.isEmpty()) {
            return
        }
        loadDataJob?.cancel()
        loadDataJob = transactionUseCases.getTransactionsForAccountWithinDateRange(
            accountId = accountId,
            startDate = dateRange.first,
            endDate = dateRange.second,
            filterBy = null,
            sortBy = sortBy
        ).onEach { transactionList ->
            _state.value = state.value.copy(
                income = transactionList.filter { it.type == TransactionType.INCOME },
                expenses = transactionList.filter { it.type == TransactionType.EXPENSE }
            )

        }.launchIn(viewModelScope)
    }


}