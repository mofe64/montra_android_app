package com.nubari.montra.domain.repository

import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.data.remote.responses.CreateAccountResponse

interface AccountRepository {
    suspend fun createAccount(request: AccountCreationRequest): CreateAccountResponse
}