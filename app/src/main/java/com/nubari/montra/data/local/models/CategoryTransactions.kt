package com.nubari.montra.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryTransactions(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val transactions: List<Transaction>
)