package com.nubari.montra.transaction.state

data class TransactionReportPreviewState(
    val monthExpenses: String = "0",
    val monthIncome: String = "0",
    val biggestSpendAmount: String = "0",
    val biggestSpendCategory: String = "",
    val biggestIncomeAmount: String = "0",
    val biggestIncomeCategory: String = "",
    val exceededBudgets: List<String> = emptyList(),
    val randomQuote: Pair<String, String> = Pair("", ""),
)
