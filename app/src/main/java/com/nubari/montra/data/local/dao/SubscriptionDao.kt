package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Subscription
import com.nubari.montra.data.local.models.SubscriptionTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {

    @Query("select * from subscriptions")
    fun getMySubscriptions(): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSubscription(subscription: Subscription)

    @Query("select * from subscriptions where id = :id")
    suspend fun getSubscription(id: String): Subscription?

    @Delete
    suspend fun deleteSubscription(subscription: Subscription)

    @Transaction
    @Query("select * from subscriptions where id = :id")
    fun getSubscriptionsTransactions(id: String): Flow<List<SubscriptionTransactions>>
}