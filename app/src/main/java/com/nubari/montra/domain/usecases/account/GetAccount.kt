package com.nubari.montra.domain.usecases.account

import com.nubari.montra.data.local.models.Account
import com.nubari.montra.domain.repository.AccountRepository

class GetAccount(
    private val repository: AccountRepository
) {
    suspend operator fun invoke(id: String): Account? {
        return repository.getAccount(id)
    }
}