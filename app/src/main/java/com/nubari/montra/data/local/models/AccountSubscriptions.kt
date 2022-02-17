package com.nubari.montra.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class AccountSubscriptions(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val list: List<Subscription>
)
