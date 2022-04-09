package com.nubari.montra.budget.state

import com.nubari.montra.data.local.models.Budget
import java.util.*

data class AllBudgetsState(
    val budgets: List<Budget> = emptyList(),
)
