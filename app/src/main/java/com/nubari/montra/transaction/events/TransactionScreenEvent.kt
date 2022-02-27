package com.nubari.montra.transaction.events

import com.nubari.montra.transaction.util.TransactionFilterBy
import com.nubari.montra.transaction.util.TransactionSortBy

sealed class TransactionScreenEvent {
    data class Filter(val filterBy: TransactionFilterBy) : TransactionScreenEvent()
    data class Sort(val sortBy: TransactionSortBy) : TransactionScreenEvent()
    object Reset : TransactionScreenEvent()
}
