package com.nubari.montra.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subscription(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,

)