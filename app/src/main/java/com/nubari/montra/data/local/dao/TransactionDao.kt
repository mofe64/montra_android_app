package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("select * from transactions where id = :id")
    suspend fun getTransaction(id: String): Transaction?

    @Query("select * from transactions where account_id = :accountId")
    fun getAllTransactionsForAccount(accountId: String): Flow<List<Transaction>>

    @Query("select * from transactions where account_id = :accountId and date between :startDate and :endDate order by date desc")
    fun getAllTransactionsForAccountWithinDatePeriod(
        accountId: String,
        startDate: Long,
        endDate: Long
    )
            : Flow<List<Transaction>>
}