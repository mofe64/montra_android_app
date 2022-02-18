package com.nubari.montra.domain.usecases.account

import com.nubari.montra.data.local.models.AccountTransactions
import com.nubari.montra.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetAccountTransactions(
    private val repository: AccountRepository
) {
    operator fun invoke(accountId: String): Flow<List<AccountTransactions>> {
        return repository.getAccountTransactions(accountId)
    }
}