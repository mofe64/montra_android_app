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
    var shouldNotify: Boolean,
    @ColumnInfo(name = "threshold")
    var threshold: Int,
    @ColumnInfo(name = "exceeded")
    var exceeded: Boolean = false,
    @ColumnInfo(name = "budget_start_date")
    val startDate: Date,
    @ColumnInfo(name = "budget_limit")
    var limit: BigDecimal,
    @ColumnInfo(name = "budget_spend")
    var spend: BigDecimal,
    @ColumnInfo(name = "category_id", index = true)
    val categoryId: String?,
    @ColumnInfo(name = "category_name")
    val categoryName: String?,
    @ColumnInfo(name = "account_id", index = true)
    val accountId: String,
    @ColumnInfo(name="date_created", defaultValue = "0")
    val dateCreated: Date
)
