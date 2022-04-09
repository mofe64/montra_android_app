package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.BudgetType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface BudgetRepository {
    suspend fun createBudget(bd: Budget)
    suspend fun saveBudget(vararg budgets: Budget)
    suspend fun getBudgetById(bdId: String): Budget?
    suspend fun deleteBudget(bd: Budget)
    fun getAllBudgets(): Flow<List<Budget>>
    suspend fun getAllExistingBudgets(): List<Budget>
    suspend fun updateBudget(bd: Budget)
    suspend fun updateBudgetSpend(spend: BigDecimal, exceeded: Boolean, id: String)
    suspend fun getBudgetWithCategoryId(categoryId: String): Budget?
    suspend fun getBudgetWithCategoryName(categoryName: String, activeAccountId: String): Budget?
    suspend fun getBudgetMatchingBudgetType(budgetType: BudgetType): List<Budget>
}