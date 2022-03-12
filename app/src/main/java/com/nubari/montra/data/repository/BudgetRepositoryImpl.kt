package com.nubari.montra.data.repository

import com.nubari.montra.data.local.dao.BudgetDao
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.domain.repository.BudgetRepository
import kotlinx.coroutines.flow.Flow

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
}