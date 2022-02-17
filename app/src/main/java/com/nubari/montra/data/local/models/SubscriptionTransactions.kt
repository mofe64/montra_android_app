package com.nubari.montra.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class SubscriptionTransactions(
    @Embedded val subscription: Subscription,
    @Relation(
        parentColumn = "id",
        entityColumn = "subscription_id"
    )
    val transactions: List<Transaction>
)
