package com.nubari.montra.domain.usecases.budget

data class BudgetUseCases(
    val createBudget: CreateBudget,
    val getBudgetByID: GetBudgetByID,
    val getBudgets: GetBudgets,
    val deleteBudget: DeleteBudget,
    val updateBudgetSpend: UpdateBudgetSpend
)