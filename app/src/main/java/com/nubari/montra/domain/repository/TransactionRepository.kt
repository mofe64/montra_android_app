package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Transaction

interface TransactionRepository {
    suspend fun createTransaction(tx: Transaction)
}