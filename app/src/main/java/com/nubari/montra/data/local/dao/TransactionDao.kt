package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("select * from transactions where id = :id")
    suspend fun getTransaction(id: String): Transaction?
}