package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository

class CreateBudget(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        repository.createBudget(bd = budget)
    }
}