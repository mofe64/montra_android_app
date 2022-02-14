package com.nubari.montra.transaction.state

import com.nubari.montra.general.state.InputState
import com.nubari.montra.general.util.InputType
import com.nubari.montra.transaction.util.TransactionType

data class TransactionFormState(
    val category: InputState = InputState(type = InputType.TEXT),
    val categories: List<String> = listOf("Food", "Shopping", "Bills", "Utilities"),
    val description: InputState = InputState(type = InputType.TEXT),
    val account: InputState = InputState(type = InputType.TEXT),
    val formValid: Boolean,
    val attachment: InputState = InputState(type = InputType.TEXT),
    val transactionType: TransactionType = TransactionType.EXPENSE,
    val isRecurring: Boolean = false
)
