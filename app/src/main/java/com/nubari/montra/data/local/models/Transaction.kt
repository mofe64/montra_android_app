package com.nubari.montra.data.local.models

import androidx.room.PrimaryKey

data class Transaction(
    @PrimaryKey(autoGenerate = false)
    val id: String
)