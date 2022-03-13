package com.nubari.montra.data.repository

import com.nubari.montra.data.local.dao.BudgetDao
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.BudgetType
import com.nubari.montra.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao
) : BudgetRepository{
    override suspend fun createBudget(bd: Budget) {
        return budgetDao.createBudget(budget = bd)
    }

    override suspend fun getBudgetById(bdId: String): Budget? {
        return  budgetDao.getBudget(id = bdId)
    }

    override suspend fun deleteBudget(bd: Budget) {
        budgetDao.deleteBudget(budget = bd)
    }

    override fun getAllBudgets(): Flow<List<Budget>> {
        return budgetDao.getBudgets()
    }

    override suspend fun updateBudget(bd: Budget) {
        budgetDao.updateBudget(budget = bd)
    }

    override suspend fun updateBudgetSpend(spend: BigDecimal, id: String) {
        budgetDao.updateBudgetSpend(spend = spend, id = id)
    }

    override suspend fun getBudgetWithCategoryId(categoryId: String): Budget? {
        return budgetDao.getBudgetWithCategoryId(categoryId = categoryId)
    }

    override suspend fun getBudgetMatchingBudgetType(budgetType: BudgetType): List<Budget> {
        return budgetDao.getBudgetByBudgetType(budgetType = budgetType)
    }
}