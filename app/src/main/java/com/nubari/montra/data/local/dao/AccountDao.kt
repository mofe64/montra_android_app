package com.nubari.montra.data.local.dao

import androidx.room.*
import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.AccountTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("select * from account")
    fun getMyAccounts(): Flow<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)

    @Query("select * from account where id = :id")
    suspend fun getAccount(id: String): Account?

    @Delete
    suspend fun deleteAccount(account: Account)

    @Transaction
    @Query("select * from account where id = :id")
    fun getAccountTransactions(id: String): Flow<List<AccountTransactions>>
}