package com.nubari.montra.budget.state

import com.nubari.montra.data.local.models.Budget
import java.util.*

data class AllBudgetsState(
    val budgets: List<Budget> = emptyList(),
    val monthsBudgets: List<Budget> = emptyList(),
    val currentMonth: Int = Calendar.getInstance(Locale.getDefault()).get(Calendar.MONTH),
)
