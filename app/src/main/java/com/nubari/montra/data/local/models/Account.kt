package com.nubari.montra.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Account(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val balance: BigDecimal

)