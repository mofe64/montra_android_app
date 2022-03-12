package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Budget
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createBudget(budget: Budget)

    @Delete
    suspend fun deleteBudget(budget: Budget)

    @Query("SELECT * FROM budget")
    fun getBudgets(): Flow<List<Budget>>

    @Query("SELECT * FROM BUDGET WHERE id = :id")
    suspend fun getBudget(id: String): Budget?

    @Query("UPDATE budget set budget_spend= :spend WHERE id = :id")
    suspend fun updateBudgetSpend(spend: BigDecimal, id: String)

    @Update
    suspend fun updateBudget(budget: Budget)

}
