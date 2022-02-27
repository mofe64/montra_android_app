package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.TransactionFrequency
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
    @ColumnInfo(name = "category_name", defaultValue = "General")
    val categoryName: String,
    @ColumnInfo(name = "subscription_id")
    val subscriptionId: String?,
    @ColumnInfo(name = "date", index = true)
    val date: Date,
    @ColumnInfo(name = "transaction_type")
    val type: TransactionType,
    @ColumnInfo(name = "amount")
    val amount: BigDecimal,
    @ColumnInfo(name = "is_recurring")
    val isRecurring: Boolean,
    @ColumnInfo(name = "transaction_name")
    val name: String,
    @ColumnInfo(name = "transaction_description")
    val description: String,
    @ColumnInfo(name = "attachment_local")
    val attachmentLocal: String?,
    @ColumnInfo(name = "attachment_remote")
    val attachmentRemote: String?,
    @ColumnInfo(name = "transaction_frequency")
    val frequency: TransactionFrequency?
)