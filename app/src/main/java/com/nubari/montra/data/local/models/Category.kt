package com.nubari.montra.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @ColumnInfo(name = "category_name")
    val name: String,
    @ColumnInfo(name = "icon_name")
    val iconName: String
)
