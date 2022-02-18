package com.nubari.montra.home.state

import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.AccountTransactions

data class HomeState(
    val account: Account?,
    val accountTransactions: AccountTransactions?,
    val income: String = "0",
    val expenses: String = "0",
)
