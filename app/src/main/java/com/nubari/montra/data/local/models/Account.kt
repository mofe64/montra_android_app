package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Account(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "account_name")
    val name: String,
    @ColumnInfo(name = "account_balance")
    val balance: BigDecimal

)