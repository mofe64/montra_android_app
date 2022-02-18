package com.nubari.montra.domain.usecases.account

import com.nubari.montra.data.local.models.Account
import com.nubari.montra.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetAllAccounts(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> {
        return repository.getAccounts()
    }
}