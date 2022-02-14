package com.nubari.montra.transaction.events

sealed class TransactionFormEvent {
    data class ChangedCategory(val category: String) : TransactionFormEvent()
    data class EnteredDescription(val value: String) : TransactionFormEvent()
    data class ChangedAccount(val accountId: String) : TransactionFormEvent()
    data class AddedAttachment(val attachment: String) : TransactionFormEvent()
    object ToggledRepeatTransaction : TransactionFormEvent()
    data class FocusChange(val focusFieldName: String) : TransactionFormEvent()
}
