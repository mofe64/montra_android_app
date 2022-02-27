package com.nubari.montra.domain.usecases.transaction

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.repository.TransactionRepository
import com.nubari.montra.transaction.util.TransactionFilterBy
import com.nubari.montra.transaction.util.TransactionSortBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTransactionsForAccount(
    private val repository: TransactionRepository
) {
    operator fun invoke(
        filterBy: TransactionFilterBy?,
        sortBy: TransactionSortBy?,
        accountId: String
    ): Flow<List<Transaction>> {
        var result = repository.getTransactionsOnAccount(accountId)
        filterBy?.let { filter ->
            result = result.map { list ->
                list.filter {
                    when (filter) {
                        TransactionFilterBy.EXPENSE -> {
                            it.type == TransactionType.EXPENSE
                        }
                        TransactionFilterBy.INCOME -> {
                            it.type == TransactionType.INCOME
                        }
                    }
                }
            }
        }
        sortBy?.let { sort ->
            result = result.map { list ->
                when (sort) {
                    TransactionSortBy.AMOUNT_ASC -> list.sortedBy { it.amount }
                    TransactionSortBy.AMOUNT_DESC -> list.sortedByDescending { it.amount }
                    TransactionSortBy.DATE_ASC -> list.sortedBy { it.date }
                    TransactionSortBy.DATE_DESC -> list.sortedByDescending { it.date }
                }
            }
        }
        return  result
    }
}