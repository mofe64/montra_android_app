package com.nubari.montra.data.repository

import com.nubari.montra.data.local.dao.TransactionDao
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun createTransaction(tx: Transaction) {
        transactionDao.createTransaction(tx)
    }
}