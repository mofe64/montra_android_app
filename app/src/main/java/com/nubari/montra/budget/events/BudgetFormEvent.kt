package com.nubari.montra.budget.events

import com.nubari.montra.data.local.models.enums.BudgetType

sealed class BudgetFormEvent {
    data class ChangedCategory(val category: String) : BudgetFormEvent()
    data class ChangeBudgetType(val budgetType: BudgetType) : BudgetFormEvent()
    data class EnteredAmount(val amount: String) : BudgetFormEvent()
    data class SetThreshold(val thresholdRaw: Float) : BudgetFormEvent()
    object ToggleReminder : BudgetFormEvent()
    data class CreateBudget(
        val category: String,
        val amount: String,
        val shouldRemind: Boolean,
        val thresholdRaw: Float,
        val budgetType: BudgetType
    ) : BudgetFormEvent()
    data class FocusChange(val fieldName: String): BudgetFormEvent()

}
