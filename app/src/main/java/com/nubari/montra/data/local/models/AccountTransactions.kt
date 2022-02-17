package com.nubari.montra.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class AccountTransactions(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val transactions: List<Transaction>
)
