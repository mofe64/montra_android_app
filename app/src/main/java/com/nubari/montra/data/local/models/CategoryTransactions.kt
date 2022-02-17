package com.nubari.montra.data.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryTransactions(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val transactions: List<Transaction>
)