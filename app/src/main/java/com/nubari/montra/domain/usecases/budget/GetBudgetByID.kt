package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository

class GetBudgetByID(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id: String): Budget? {
        return repository.getBudgetById(bdId = id)
    }
}