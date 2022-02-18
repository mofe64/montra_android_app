package com.nubari.montra.transaction.events

import com.nubari.montra.data.local.models.enums.TransactionType

sealed class TransactionFormEvent {
    data class ChangedCategory(val category: String) : TransactionFormEvent()
    data class EnteredDescription(val value: String) : TransactionFormEvent()
    data class ChangedAccount(val accountId: String) : TransactionFormEvent()
    data class AddedAttachment(val attachment: String) : TransactionFormEvent()
    object ToggledRepeatTransaction : TransactionFormEvent()
    data class FocusChange(val focusFieldName: String) : TransactionFormEvent()
    data class EnteredAmount(val amount: String) : TransactionFormEvent()
    data class CreateTransaction(
        val categoryName: String,
        val description: String,
        val amount: String,
        val type: TransactionType
    ) : TransactionFormEvent()
}
