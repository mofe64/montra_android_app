package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun createTransaction(tx: Transaction)
    suspend fun getTransactionById(txId: String) : Transaction?
    suspend fun deleteTransaction(tx: Transaction)
    fun getTransactionsOnAccount(id: String): Flow<List<Transaction>>
    fun getTransactionsOnAccountWithinDateRange(
        id: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>>
}