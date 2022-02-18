package com.nubari.montra.home.state

import com.nubari.montra.data.local.models.Account

data class HomeState(
    val account: Account?,
    val income: String = "",
    val expenses: String = "",
)
