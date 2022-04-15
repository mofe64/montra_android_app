package com.nubari.montra.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nubari.montra.TestUtil
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.local.models.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class CategoryDaoTest {
    private lateinit var db: MontraDatabase
    private lateinit var categoryDao: CategoryDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var category1: Category
    private lateinit var category2: Category
    private lateinit var category3: Category

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MontraDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
        categoryDao = db.categoryDao
        val categories = TestUtil.generateMultipleCategories(3);
        category1 = categories[0]
        category2 = categories[1]
        category3 = categories[2]

    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testGetAllCategories() {
        testScope.runBlockingTest {
            categoryDao.createCategory(category1)
            categoryDao.createCategory(category2)
            categoryDao.createCategory(category3)

            val categoryLists = categoryDao.getAllCategories().take(1)
                .toList()[0]
            assertThat(categoryLists.size).isEqualTo(3)
            assertThat(categoryLists).contains(category1)
            assertThat(categoryLists).contains(category2)
            assertThat(categoryLists).contains(category3)
        }
    }

    @Test
    fun testCreateCategory() {
        testScope.runBlockingTest {
            categoryDao.createCategory(category1)
            val savedCategory = categoryDao.getCategory(category1.id)
            assertThat(savedCategory).isEqualTo(category1)
        }
    }

    @Test
    fun testGetCategory() {
        testScope.runBlockingTest {
            categoryDao.createCategory(category1)
            categoryDao.createCategory(category3)
            val savedCategory = categoryDao.getCategory(category1.id)
            assertThat(savedCategory).isEqualTo(category1)
        }
    }

    @Test
    fun testDeleteCategory() {
        testScope.runBlockingTest {
            categoryDao.createCategory(category1)
            categoryDao.createCategory(category2)
            categoryDao.createCategory(category3)

            var categoryLists = categoryDao.getAllCategories().take(1)
                .toList()[0]
            assertThat(categoryLists.size).isEqualTo(3)
            assertThat(categoryLists).contains(category1)
            assertThat(categoryLists).contains(category2)
            assertThat(categoryLists).contains(category3)

            categoryDao.deleteCategory(category2)

            categoryLists = categoryDao.getAllCategories().take(1)
                .toList()[0]

            assertThat(categoryLists.size).isEqualTo(2)
            assertThat(categoryLists).contains(category1)
            assertThat(categoryLists).doesNotContain(category2)
            assertThat(categoryLists).contains(category3)


        }
    }


}