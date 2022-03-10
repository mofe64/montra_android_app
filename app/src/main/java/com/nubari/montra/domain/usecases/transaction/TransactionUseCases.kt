package com.nubari.montra.domain.usecases.transaction

data class TransactionUseCases(
    val createTransaction: CreateTransaction,
    val getTransactionsForAccount: GetTransactionsForAccount,
    val getTransactionsForAccountWithinDateRange: GetTransactionsForAccountWithinDateRange,
    val getTransactionById: GetTransactionById,
)
