package com.nubari.montra.domain.usecases.transaction

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.domain.repository.TransactionRepository

class DeleteTransaction(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(tx: Transaction) {
        repository.deleteTransaction(tx)
    }
}