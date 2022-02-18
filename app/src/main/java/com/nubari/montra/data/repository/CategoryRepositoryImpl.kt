package com.nubari.montra.data.repository

import android.util.Log
import com.nubari.montra.data.local.dao.CategoryDao
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun createCategory(category: Category) {
        categoryDao.createCategory(category)
    }

    override fun getCategories(): Flow<List<Category>> {
        val x = categoryDao.getAllCategories()
        Log.i("repo", x.toString())
        x.onEach {
            Log.i("repo", it.toString())
        }
        return x
    }
}