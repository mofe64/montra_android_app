package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun createCategory(category: Category)
    fun getCategories(): Flow<List<Category>>
}