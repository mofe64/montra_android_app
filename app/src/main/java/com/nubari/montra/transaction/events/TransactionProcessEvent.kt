package com.nubari.montra.transaction.events

sealed class TransactionProcessEvent {
    object TransactionCreationSuccess : TransactionProcessEvent()
    data class TransactionCreationFail(val message: String) : TransactionProcessEvent()
}
