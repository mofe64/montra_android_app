package com.nubari.montra

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.BudgetType
import java.math.BigDecimal
import java.util.*

object TestUtil {
    fun generateMultipleBudgets(count: Int): List<Budget> {
        val budgets: MutableList<Budget> = mutableListOf();
        for (i in 1..count) {
            val budget = Budget(
                "budget$i",
                BudgetType.CATEGORY,
                shouldNotify = true,
                100,
                exceeded = false,
                Date(),
                BigDecimal(10000),
                BigDecimal.ZERO,
                "testCategoryId$i",
                "Food$i",
                "test123",
                Date()
            )
            budgets.add(budget)
        }
        return budgets;
    }
}