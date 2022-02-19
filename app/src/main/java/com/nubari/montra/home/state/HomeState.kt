package com.nubari.montra.home.state

import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.AccountTransactions
import com.nubari.montra.data.local.models.Transaction
import java.util.*

data class HomeState(
    val currentMonth: Int = Calendar.getInstance(Locale.getDefault()).get(Calendar.MONTH),
    val account: Account?,
    val accountTransactions: AccountTransactions?,
    val recentTransactions: List<Transaction>?,
    val monthsTransactions: List<Transaction>?,
    val spendingData: List<Double>?,
    val income: String = "0",
    val expenses: String = "0",
)
