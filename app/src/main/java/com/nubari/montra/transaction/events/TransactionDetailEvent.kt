package com.nubari.montra.transaction.events

import com.nubari.montra.data.local.models.Transaction

sealed class TransactionDetailEvent {
    data class DeleteTransaction(val tx: Transaction) : TransactionDetailEvent()
}
