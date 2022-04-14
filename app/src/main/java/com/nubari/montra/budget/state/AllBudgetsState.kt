package com.nubari.montra.budget.state

import com.nubari.montra.data.local.models.Budget

data class AllBudgetsState(
    val budgets: List<Budget> = emptyList(),
)
