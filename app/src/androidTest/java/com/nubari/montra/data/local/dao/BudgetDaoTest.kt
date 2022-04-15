package com.nubari.montra.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nubari.montra.TestUtil
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.local.models.Budget
import com.nubari.montra.data.local.models.enums.BudgetType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import timber.log.Timber

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class BudgetDaoTest {
    private lateinit var db: MontraDatabase
    private lateinit var budgetDao: BudgetDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var budget: Budget
    private lateinit var budget2: Budget
    private val tag = "Budget_Dao_test"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MontraDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()

        budgetDao = db.budgetDao
        val budgets = TestUtil.generateMultipleBudgets(2);
        budget = budgets[0]
        budget2 = budgets[1]
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testCreateBudget() {
        testScope.runBlockingTest {
            budgetDao.createBudget(budget);
            val savedBudget = budgetDao.getBudget(budget.id)
            assertThat(savedBudget).isNotNull()
            assertThat(savedBudget!!.id).isEqualTo(budget.id)
        }
    }

    @Test
    fun testSaveBudgets() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())

            val budgetLists = budgetDao.getBudgets().take(1)
                .toList()[0]
            Timber.tag(tag).d(budgetLists.toString())
            assertThat(budgetLists.size).isEqualTo(2)
            assertThat(budgetLists[0].id).isEqualTo(budget.id)
        }
    }

    @Test
    fun testDeleteBudget() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())

            var budgetLists = budgetDao.getBudgets().take(1)
                .toList()[0]
            Timber.tag(tag).d(budgetLists.toString())
            assertThat(budgetLists.size).isEqualTo(2)
            assertThat(budgetLists[0].id).isEqualTo(budget.id)

            budgetDao.deleteBudget(budget)
            budgetLists = budgetDao.getBudgets().take(1)
                .toList()[0]
            assertThat(budgetLists.size).isEqualTo(1)
            assertThat(budgetLists[0].id).isEqualTo(budget2.id)

        }
    }

    @Test
    fun testGetBudgets() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())

            val budgetLists = budgetDao.getBudgets().take(1)
                .toList()[0]
            assertThat(budgetLists.size).isEqualTo(2)
        }
    }

    @Test
    fun testRetrieveBudgetList() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            val budgets = budgetDao.retrieveBudgetList()
            assertThat(budgets.size).isEqualTo(2)
        }
    }

    @Test
    fun testGetBudget() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            val savedBudget = budgetDao.getBudget(budget2.id)
            assertThat(savedBudget).isNotNull()
            assertThat(savedBudget!!.id).isEqualTo(budget2.id)
        }
    }

    @Test
    fun testUpdateBudgetSpend() {
        testScope.runBlockingTest {
            val oldSpend = budget.spend
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            budgetDao.updateBudgetSpend(BigDecimal.valueOf(10000), true, budget.id)
            val updatedBudget = budgetDao.getBudget(budget.id)
            assertThat(oldSpend).isNotEqualTo(updatedBudget!!.spend)
            assertThat(updatedBudget.exceeded).isTrue()
        }
    }

    @Test
    fun testGetBudgetWithCategoryId() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            val foundBudget = budgetDao.getBudgetWithCategoryId(budget.categoryId!!)
            assertThat(foundBudget).isNotNull()
            assertThat(foundBudget!!.categoryId!!).isEqualTo(budget.categoryId!!)
        }
    }

    @Test
    fun testGetBudgetWithCategoryName() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            val foundBudget = budgetDao.getBudgetWithCategoryName(budget.categoryName!!, "test123")
            assertThat(foundBudget).isNotNull()
            assertThat(foundBudget!!.categoryName!!).isEqualTo(budget.categoryName!!)
        }
    }


    @Test
    fun testGetBudgetByBudgetType() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            val matchingBudgets = budgetDao.getBudgetByBudgetType(BudgetType.CATEGORY)
            assertThat(matchingBudgets).contains(budget)
            assertThat(matchingBudgets).contains(budget2)
        }
    }

    @Test
    fun testUpdateBudget() {
        testScope.runBlockingTest {
            budgetDao.saveBudgets(*(listOf(budget, budget2)).toTypedArray())
            budget.spend = BigDecimal.valueOf(100_000)
            budget.limit = BigDecimal.valueOf(100_000_000)
            budgetDao.updateBudget(budget)
            val updatedBudget = budgetDao.getBudget(budget.id)
            assertThat(updatedBudget!!.spend).isEqualToIgnoringScale(100_000)
            assertThat(updatedBudget.limit).isEqualToIgnoringScale(100_000_000)
        }
    }
}