package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.SubscriptionType
import java.util.*

@Entity(
    tableName = "subscriptions",
)
data class Subscription(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "subscription_name")
    val name: String,
    @ColumnInfo(name = "account_id", index = true)
    val accountId: String,
    @ColumnInfo(name = "due_date", index = true)
    val dueDate: Date,
    @ColumnInfo(name = "subscription_type")
    val type: SubscriptionType

)