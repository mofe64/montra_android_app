package com.nubari.montra.transaction.state

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.models.CategoryBreakdown

data class TransactionReportState(
    val monthExpenses: String = "0",
    val monthIncome: String = "0",
    val biggestSpendAmount: String = "0",
    val biggestSpendCategory: String = "",
    val biggestIncomeAmount: String = "0",
    val biggestIncomeCategory: String = "",
    val exceededBudgets: List<String> = emptyList(),
    val randomQuote: Pair<String, String> = Pair("", ""),
    val expenses: List<Transaction> = emptyList(),
    val income: List<Transaction> = emptyList(),
    val categoryBreakDown: List<CategoryBreakdown> = emptyList(),
    val incomeSpendingData: List<Double> = emptyList(),
    val expenseSpendingData: List<Double> = emptyList(),
    val categoryIncomePieChartData : List<Float> = emptyList(),
    val categoryExpensePieChartData : List<Float> = emptyList(),
    val transactionsToView: String = "",
    val activeTab: String = "",
    val activeTabView: String = "Transactions",
    val sortDir: String = "desc",
    val activeGraph: String = "line"
)
