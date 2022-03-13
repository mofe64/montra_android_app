package com.nubari.montra.budget.state

import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.general.state.InputState
import com.nubari.montra.general.util.InputType

data class BudgetFormState(
    val limit: InputState = InputState(type = InputType.NUMBER),
    val category: InputState = InputState(type = InputType.TEXT),
    val shouldAlert: Boolean = false,
    val threshold: Float = 0f,
    val budgetType: BudgetType = BudgetType.CATEGORY,
    val categories: List<Category> = emptyList(),
    val formValid : Boolean = true,
    val isProcessing: Boolean = false
)
