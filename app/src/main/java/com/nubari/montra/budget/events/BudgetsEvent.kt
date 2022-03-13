package com.nubari.montra.budget.events

sealed class BudgetsEvent{
    data class ChangeMonth(val newMonth: Int) : BudgetsEvent()
}
