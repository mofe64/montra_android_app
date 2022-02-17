package com.nubari.montra.data.repository

import com.nubari.montra.data.local.dao.AccountDao
import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.remote.requests.AccountCreationRequest

import com.nubari.montra.data.remote.responses.CreateAccountResponse
import com.nubari.montra.domain.repository.AccountRepository
import java.math.BigDecimal

class AccountRepositoryImpl(
    private val api: MontraApi,
    private val accountDao: AccountDao
) : AccountRepository {

    override suspend fun createAccount(request: AccountCreationRequest): CreateAccountResponse {
        val response = api.newAccount(request = request)
        val account = Account(
            id = request.id,
            balance = BigDecimal(request.balance),
            name = request.name
        )
        accountDao.createAccount(account = account)
        return response
    }
}