package com.nubari.montra.transaction.events

import com.nubari.montra.data.local.models.Budget

sealed class TransactionProcessEvent {
    object TransactionCreationSuccess : TransactionProcessEvent()
    data class BudgetExceeded(
        val budget: Budget
    ) : TransactionProcessEvent()

    data class TransactionCreationFail(val message: String) : TransactionProcessEvent()
    object TransactionDeleteSuccess : TransactionProcessEvent()
}
