package com.nubari.montra.domain.usecases.category

import com.nubari.montra.data.local.models.Category
import com.nubari.montra.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategories(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> {
        return repository.getCategories()
    }
}