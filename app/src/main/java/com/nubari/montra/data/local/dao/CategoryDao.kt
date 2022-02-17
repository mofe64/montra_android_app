package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Category
import com.nubari.montra.data.local.models.CategoryTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("select * from category")
    fun getAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("select * from category where id = :id")
    suspend fun getCategory(id: String): Category?

    @Transaction
    @Query("select * from category where id = :id")
    fun getTransactionsWithinACategory(id: String): Flow<List<CategoryTransactions>>
}