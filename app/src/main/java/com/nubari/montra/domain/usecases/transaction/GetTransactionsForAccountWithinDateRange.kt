package com.nubari.montra.domain.usecases.transaction

import android.annotation.SuppressLint
import android.util.Log
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.repository.TransactionRepository
import com.nubari.montra.transaction.util.TransactionFilterBy
import com.nubari.montra.transaction.util.TransactionSortBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTransactionsForAccountWithinDateRange(
    private val repository: TransactionRepository
) {
    @SuppressLint("LongLogTag")
    operator fun invoke(
        accountId: String,
        startDate: Long,
        endDate: Long,
        filterBy: TransactionFilterBy?,
        sortBy: TransactionSortBy?
    ): Flow<List<Transaction>> {
        var result = repository.getTransactionsOnAccountWithinDateRange(
            id = accountId,
            startDate = startDate,
            endDate = endDate
        )
        Log.i("Get-Transactions-For-Account-Within-Date-Range", result.toString())
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
                Log.i("Get-Transactions-For-Account-Within-Date-Range", list.toString())
                when (sort) {
                    TransactionSortBy.AMOUNT_ASC -> list.sortedBy { it.amount }
                    TransactionSortBy.AMOUNT_DESC -> list.sortedByDescending { it.amount }
                    TransactionSortBy.DATE_ASC -> list.sortedBy { it.date }
                    TransactionSortBy.DATE_DESC -> list.sortedByDescending { it.date }
                }
            }
        }

        return result
    }
}