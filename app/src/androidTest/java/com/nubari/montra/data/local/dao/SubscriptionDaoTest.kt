package com.nubari.montra.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nubari.montra.TestUtil
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.local.models.Subscription
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
class SubscriptionDaoTest {
    private lateinit var db: MontraDatabase
    private lateinit var subscriptionDao: SubscriptionDao
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var subscription1: Subscription
    private lateinit var subscription2: Subscription


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MontraDatabase::class.java
        )
            .setTransactionExecutor(testDispatcher.asExecutor())
            .setQueryExecutor(testDispatcher.asExecutor())
            .build()
        subscriptionDao = db.subscriptionDao
        val subs = TestUtil.generateMultipleSubscriptions(3)
        subscription1 = subs[0]
        subscription2 = subs[1]
    }

    @After
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun testGetMySubs() {
        testScope.runBlockingTest {
            subscriptionDao.createSubscription(subscription1)
            subscriptionDao.createSubscription(subscription2)

            val subs = subscriptionDao.getMySubscriptions()
                .take(1).toList()[0]

            assertThat(subs.size).isEqualTo(2)
            assertThat(subs).contains(subscription1)
            assertThat(subs).contains(subscription2)
        }
    }

    @Test
    fun testCreateSub() {
        testScope.runBlockingTest {
            subscriptionDao.createSubscription(subscription1)

            val subs = subscriptionDao.getMySubscriptions()
                .take(1).toList()[0]

            assertThat(subs.size).isEqualTo(1)
            assertThat(subs).contains(subscription1)
        }
    }

    @Test
    fun testGetSub() {
        testScope.runBlockingTest {
            subscriptionDao.createSubscription(subscription1)
            val sub = subscriptionDao.getSubscription(subscription1.id)
            assertThat(sub).isNotNull()
            assertThat(sub).isEqualTo(subscription1)
        }
    }

    @Test
    fun testDeleteSub() {
        testScope.runBlockingTest {
            subscriptionDao.createSubscription(subscription1)
            subscriptionDao.createSubscription(subscription2)

            var subs = subscriptionDao.getMySubscriptions()
                .take(1).toList()[0]

            assertThat(subs.size).isEqualTo(2)
            assertThat(subs).contains(subscription1)
            assertThat(subs).contains(subscription2)

            subscriptionDao.deleteSubscription(subscription1)

            subs = subscriptionDao.getMySubscriptions()
                .take(1).toList()[0]
            assertThat(subs.size).isEqualTo(1)
            assertThat(subs).contains(subscription2)
            assertThat(subs).doesNotContain(subscription1)


        }
    }

}