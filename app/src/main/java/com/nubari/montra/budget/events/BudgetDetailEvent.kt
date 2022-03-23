package com.nubari.montra.budget.events

import com.nubari.montra.data.local.models.Budget

sealed class BudgetDetailEvent {
    data class DeleteBudget(val bd: Budget) : BudgetDetailEvent()
}
