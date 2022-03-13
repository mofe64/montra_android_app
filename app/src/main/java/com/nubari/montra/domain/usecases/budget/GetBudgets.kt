package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

class GetBudgets(
    private val repository: BudgetRepository
) {
    operator fun invoke() : Flow<List<Budget>> {
        return repository.getAllBudgets()
    }
}