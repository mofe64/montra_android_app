package com.nubari.montra.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.local.models.Account
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
import timber.log.Timber
import java.math.BigDecimal
import com.google.common.truth.Truth.assertThat
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AccountDaoTest {
    private val tag = "Account_Dao_Test"
    private lateinit var db: MontraDatabase
    private lateinit var accountDao: AccountDao
    private lateinit var transactionDao: TransactionDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var account: Account
    private lateinit var account2: Account
    private lateinit var tx1: Transaction
    private lateinit var tx2: Transaction

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MontraDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
        accountDao = db.accountDao
        transactionDao = db.transactionDao
        account = Account(
            id = "testId",
            name = "testName",
            balance = BigDecimal.ZERO
        )
        account2 = Account(
            id = "testId2",
            name = "testName2",
            balance = BigDecimal.TEN
        )
        tx1 = Transaction(
            id = "testIdTx",
            amount = BigDecimal.TEN,
            categoryName = "test",
            categoryId = "1",
            date = Date(),
            accountId = "testId",
            type = TransactionType.EXPENSE,
            description = "test description",
            name = "test tx 1",
            isRecurring = false,
            frequency = null,
            attachmentLocal = null,
            attachmentRemote = null,
            subscriptionId = null
        )
        tx2 = Transaction(
            id = "testIdTx2",
            amount = BigDecimal.TEN,
            categoryName = "test",
            categoryId = "1",
            date = Date(),
            accountId = "testId2",
            type = TransactionType.EXPENSE,
            description = "test description",
            name = "test tx 2",
            isRecurring = false,
            frequency = null,
            attachmentLocal = null,
            attachmentRemote = null,
            subscriptionId = null
        )
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testGetMyAccounts() {
        testScope.runBlockingTest {
            accountDao.insert(account)
            accountDao.insert(account2)

            val accounts = accountDao
                .getMyAccounts().take(1).toList()[0]
            Timber.tag(tag).d(accounts.toString())
            assertThat(accounts.size).isEqualTo(2)
        }
    }

    @Test
    fun testInsert() {
        testScope.runBlockingTest {
            accountDao.insert(account = account)
            val accounts = accountDao
                .getMyAccounts().take(1).toList()[0]
            assertThat(accounts.size).isEqualTo(1);
            assertThat(accounts[0].id).isEqualTo("testId")
            assertThat(accounts[0].name).isEqualTo("testName")
        }
    }

    @Test
    fun testGetAccount() {
        testScope.runBlockingTest {
            accountDao.insert(account)
            accountDao.insert(account2)

            val queriedAccount = accountDao.getAccount("testId")
            assertThat(queriedAccount).isNotNull()
            assertThat(queriedAccount!!.name).isEqualTo(account.name)
            assertThat(queriedAccount.balance).isEqualToIgnoringScale(account.balance)
        }
    }

    @Test
    fun testDeleteAccount() {
        testScope.runBlockingTest {
            accountDao.insert(account)
            accountDao.insert(account2)

            var accounts = accountDao
                .getMyAccounts().take(1).toList()[0]
            assertThat(accounts.size).isEqualTo(2);

            accountDao.deleteAccount(account2)

            accounts = accountDao
                .getMyAccounts().take(1).toList()[0]
            assertThat(accounts.size).isEqualTo(1);

            assertThat(accounts[0].name).isEqualTo(account.name)
        }
    }

    @Test
    fun testGetAccountTransactions() {
        testScope.runBlockingTest {
            accountDao.insert(account)
            accountDao.insert(account2)

            transactionDao.createTransaction(tx1)
            transactionDao.createTransaction(tx2)

            val accountTx = accountDao.getAccountTransactions("testId")
            val accountTx2 = accountDao.getAccountTransactions("testId2")

            assertThat(accountTx.transactions.size).isEqualTo(1)
            assertThat(accountTx2.transactions.size).isEqualTo(1)

            assertThat(accountTx.transactions[0].name).isEqualTo("test tx 1")
        }
    }


    @Test
    fun testUpdateAccountBalance() {
        testScope.runBlockingTest {
            accountDao.insert(account = account)
            accountDao.updateAccountBalance(accountBalance = BigDecimal(1000), account.id)
            val updatedAccount = accountDao.getAccount(account.id)
            assertThat(updatedAccount).isNotNull()
            assertThat(updatedAccount!!.balance).isEqualToIgnoringScale(BigDecimal(1000))
        }
    }

}