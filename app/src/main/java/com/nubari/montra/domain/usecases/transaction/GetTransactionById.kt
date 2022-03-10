package com.nubari.montra.domain.usecases.transaction

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.domain.repository.TransactionRepository

class GetTransactionById(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Transaction? {
        return repository.getTransactionById(txId = id)
    }
}