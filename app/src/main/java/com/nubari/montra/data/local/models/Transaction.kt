package com.nubari.montra.data.local.models

import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.TransactionType
import java.math.BigDecimal
import java.util.*

data class Transaction(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val accountId: String,
    val categoryId: String,
    val subscriptionId: String?,
    val date: Date,
    val type: TransactionType,
    val amount: BigDecimal,
    val isRecurring: Boolean
)