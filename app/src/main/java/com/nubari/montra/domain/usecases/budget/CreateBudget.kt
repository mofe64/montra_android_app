package com.nubari.montra.domain.usecases.budget

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.domain.exceptions.BudgetException
import com.nubari.montra.domain.repository.BudgetRepository

class CreateBudget(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budget: Budget) {
        /** Extract categoryId from the budget object **/
        val providedCategoryId = budget.categoryId ?: ""
        if (providedCategoryId.isEmpty()) {
            /** If user chose to create a general budget
             * check if there is an existing general budget in db
             * **/
            val existingGeneralBudgetList =
                repository.getBudgetMatchingBudgetType(BudgetType.GENERAL)
            if (existingGeneralBudgetList.isNotEmpty()) {
                throw BudgetException(
                    "You cannot have more than one active general budget at a time.\n" +
                            "Delete existing general budget or edit it"
                )
            }
        } else {
            /** Check if budget has already been created for provided category **/
            val existingBudgetForProvidedCategory =
                repository.getBudgetWithCategoryId(providedCategoryId)
            existingBudgetForProvidedCategory?.let {
                throw BudgetException(
                    "You cannot have more than one active ${budget.categoryName} budget at a time.\n" +
                            "Delete existing ${budget.categoryName}  budget or edit it"
                )
            }
        }
        /** If there are no existing matching category or general budgets create a new budget **/
        repository.createBudget(bd = budget)
    }
}