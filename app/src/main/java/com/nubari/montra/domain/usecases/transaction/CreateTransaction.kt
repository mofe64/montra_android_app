package com.nubari.montra.domain.usecases.transaction

import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.local.models.enums.TransactionType
import com.nubari.montra.domain.repository.AccountRepository
import com.nubari.montra.domain.repository.TransactionRepository

class CreateTransaction(
    private val repository: TransactionRepository,
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(transaction: Transaction) {
        val accountId = transaction.accountId
        val transactionAmount = transaction.amount
        val transactionType = transaction.type
        val account = accountRepository.getAccount(accountId)
        //Todo(Some validation if account exists or not)
        repository.createTransaction(tx = transaction)
        account?.let {
            when (transactionType) {
                TransactionType.INCOME -> {
                    val balance = it.balance.add(transactionAmount)
                    accountRepository.updateAccountBalance(balance = balance, id = accountId)
                }
                TransactionType.EXPENSE -> {
                    val balance = it.balance.minus(transactionAmount)
                    accountRepository.updateAccountBalance(balance = balance, id = accountId)
                }
            }
        }
    }
}