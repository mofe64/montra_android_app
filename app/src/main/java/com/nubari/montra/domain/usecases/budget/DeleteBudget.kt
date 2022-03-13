package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository

class DeleteBudget(
    private val repository: BudgetRepository
) {
    suspend operator fun  invoke(budget: Budget) {
        repository.deleteBudget(bd = budget)
    }
}