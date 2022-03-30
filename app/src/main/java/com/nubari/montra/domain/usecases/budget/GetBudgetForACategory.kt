package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository
import com.nubari.montra.preferences

class GetBudgetForACategory(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(categoryName: String): Budget? {
        val accountId = preferences.activeAccountId
        return repository.getBudgetWithCategoryName(
            categoryName = categoryName,
            activeAccountId = accountId
        )
    }
}