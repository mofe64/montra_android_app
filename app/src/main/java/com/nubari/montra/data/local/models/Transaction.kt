package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.TransactionType
import java.math.BigDecimal
import java.util.*

@Entity(
    tableName = "transactions",
)
data class Transaction(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "account_id", index = true)
    val accountId: String,
    @ColumnInfo(name = "category_id")
    val categoryId: String,
    @ColumnInfo(name = "subscription_id")
    val subscriptionId: String?,
    @ColumnInfo(name = "date", index = true)
    val date: Date,
    @ColumnInfo(name = "transaction_type")
    val type: TransactionType,
    @ColumnInfo(name = "amount")
    val amount: BigDecimal,
    @ColumnInfo(name = "is_recurring")
    val isRecurring: Boolean
)