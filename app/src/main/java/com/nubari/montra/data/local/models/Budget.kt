package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nubari.montra.data.local.models.enums.BudgetType
import java.math.BigDecimal
import java.util.*

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "budget_type")
    val budgetType: BudgetType,
    @ColumnInfo(name = "should_notify")
    val shouldNotify: Boolean,
    @ColumnInfo(name = "threshold")
    val threshold: Int,
    @ColumnInfo(name = "exceeded")
    val exceeded: Boolean = false,
    @ColumnInfo(name = "budget_start_date")
    val startDate: Date,
    @ColumnInfo(name = "budget_limit")
    val limit: BigDecimal,
    @ColumnInfo(name = "budget_spend")
    val spend: BigDecimal,
    @ColumnInfo(name = "category_id", index = true)
    val categoryId: String?,
    @ColumnInfo(name = "category_name")
    val categoryName: String?,
    @ColumnInfo(name = "account_id", index = true)
    val accountId: String,
)
