package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun createBudget(bd: Budget)
    suspend fun getBudgetById(bdId: String) : Budget?
    suspend fun deleteBudget(bd: Budget)
    fun getAllBudgets() : Flow<List<Budget>>
    suspend fun updateBudget(bd: Budget)
}