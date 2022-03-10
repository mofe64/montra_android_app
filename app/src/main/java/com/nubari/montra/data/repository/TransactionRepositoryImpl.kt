package com.nubari.montra.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.nubari.montra.data.local.dao.TransactionDao
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun createTransaction(tx: Transaction) {
        transactionDao.createTransaction(tx)
    }

    override suspend fun getTransactionById(txId: String): Transaction? {
        return transactionDao.getTransaction(id = txId)
    }

    override fun getTransactionsOnAccount(id: String): Flow<List<Transaction>> {
        return transactionDao.getAllTransactionsForAccount(accountId = id)
    }

    @SuppressLint("LongLogTag")
    override fun getTransactionsOnAccountWithinDateRange(
        id: String,
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> {
        Log.i("Transaction-Repository-Impl", id)
        Log.i("Transaction-Repository-Impl", startDate.toString())
        Log.i("Transaction-Repository-Impl", endDate.toString())
        return transactionDao.getAllTransactionsForAccountWithinDatePeriod(
            accountId = id,
            startDate = startDate,
            endDate = endDate
        )
    }
}