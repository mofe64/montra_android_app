package com.nubari.montra.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nubari.montra.TestUtil
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.local.models.Transaction
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
class TransactionDaoTest {

    private lateinit var db: MontraDatabase
    private lateinit var transactionDao: TransactionDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var tx1: Transaction
    private lateinit var tx2: Transaction
    private lateinit var tx3: Transaction


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MontraDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()

        transactionDao = db.transactionDao
        val txs = TestUtil.generateMultipleTx(3)
        tx1 = txs[0]
        tx2 = txs[1]
        tx3 = txs[2]
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }


    @Test
    fun testCreateTx() {
        testScope.runBlockingTest {
            transactionDao.createTransaction(tx1)
            val foundTx = transactionDao.getTransaction(tx1.id)
            assertThat(foundTx).isNotNull()
            assertThat(foundTx!!).isEqualTo(tx1)
        }
    }

    @Test
    fun testGetTx() {
        testScope.runBlockingTest {
            transactionDao.createTransaction(tx1)
            val foundTx = transactionDao.getTransaction(tx1.id)
            assertThat(foundTx).isNotNull()
            assertThat(foundTx!!).isEqualTo(tx1)
        }
    }

    @Test
    fun testGetAllTxForAccount() {
        testScope.runBlockingTest {
            transactionDao.createTransaction(tx1)
            transactionDao.createTransaction(tx2)
            transactionDao.createTransaction(tx3)

            val txs = transactionDao.getAllTransactionsForAccount(tx1.accountId)
                .take(1).toList()[0]

            assertThat(txs.size).isEqualTo(1)
            assertThat(txs).contains(tx1)
            assertThat(txs).doesNotContain(tx2)
            assertThat(txs).doesNotContain(tx3)
        }
    }

    @Test
    fun testDeleteTx() {
        testScope.runBlockingTest {
            transactionDao.createTransaction(tx1)
            transactionDao.createTransaction(tx2)
            transactionDao.createTransaction(tx3)

            var txs = transactionDao.getAllTransactions()
                .take(1).toList()[0]

            assertThat(txs.size).isEqualTo(3)
            assertThat(txs).contains(tx1)
            assertThat(txs).contains(tx2)
            assertThat(txs).contains(tx3)


            transactionDao.deleteTransaction(tx1)

            txs = transactionDao.getAllTransactions()
                .take(1).toList()[0]
            assertThat(txs.size).isEqualTo(2)

            assertThat(txs).doesNotContain(tx1)
            assertThat(txs).contains(tx2)
            assertThat(txs).contains(tx3)

        }
    }
}