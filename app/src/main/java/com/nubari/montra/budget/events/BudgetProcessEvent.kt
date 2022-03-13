package com.nubari.montra.budget.events

sealed class BudgetProcessEvent {
    object BudgetCreationSuccess : BudgetProcessEvent()
    data class BudgetCreationFail(val message: String) : BudgetProcessEvent()
}
