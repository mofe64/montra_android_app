package com.nubari.montra.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.SubscriptionType
import java.util.*

@Entity
data class Subscription(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val accountId: String,
    val dueDate: Date,
    val type: SubscriptionType

)