package com.nubari.montra.transaction.state

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.transaction.util.TransactionFilterBy
import com.nubari.montra.transaction.util.TransactionSortBy

data class TransactionsState(
    val transactions: List<Transaction> = emptyList(),
    val sortBy: TransactionSortBy? = TransactionSortBy.DATE_DESC,
    val filterBy: TransactionFilterBy?
)
