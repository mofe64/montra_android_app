package com.nubari.montra.data.repository

import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.data.remote.responses.CreateAccountResponse
import com.nubari.montra.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val api: MontraApi
) : AccountRepository {

    override suspend fun createAccount(request: AccountCreationRequest): CreateAccountResponse {
        return api.newAccount(request = request)
    }
}