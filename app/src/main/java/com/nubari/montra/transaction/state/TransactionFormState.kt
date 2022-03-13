package com.nubari.montra.transaction.state

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.TransactionFrequency
import com.nubari.montra.general.state.InputState
import com.nubari.montra.general.util.InputType
import com.nubari.montra.transaction.util.TransactionType

data class TransactionFormState(
    val category: InputState = InputState(type = InputType.TEXT),
    val categories: MutableMap<String, String>?,
    val amount: InputState = InputState(type = InputType.NUMBER, text = "0"),
    val description: InputState = InputState(type = InputType.TEXT),
    val account: InputState = InputState(type = InputType.TEXT),
    val formValid: Boolean,
    val attachment: InputState = InputState(type = InputType.TEXT),
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val isRecurring: Boolean = false,
    val isProcessing: Boolean = false,
    val recurringFrequency: InputState = InputState(type = InputType.TEXT),
    val frequencies: List<TransactionFrequency> = TransactionFrequency.values().toList(),
    val userBudgets: List<Budget> = emptyList()
)
