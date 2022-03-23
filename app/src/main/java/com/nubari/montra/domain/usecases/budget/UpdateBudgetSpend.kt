package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.domain.repository.BudgetRepository
import java.math.BigDecimal

class UpdateBudgetSpend(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budgetId: String, exceeded: Boolean, spend: BigDecimal) {
        repository.updateBudgetSpend(spend = spend, exceeded = exceeded, id = budgetId)
    }

}