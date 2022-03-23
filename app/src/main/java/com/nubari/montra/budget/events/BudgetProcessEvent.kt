package com.nubari.montra.budget.events

sealed class BudgetProcessEvent {
    object BudgetCreationSuccess : BudgetProcessEvent()
    object BudgetDeleteSuccess : BudgetProcessEvent()
    data class BudgetCreationFail(val message: String) : BudgetProcessEvent()
}
