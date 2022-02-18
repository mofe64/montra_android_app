package com.nubari.montra.domain.repository

import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.AccountTransactions
import com.nubari.montra.data.local.models.Transaction
import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.data.remote.responses.CreateAccountResponse
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface AccountRepository {
    suspend fun createAccount(request: AccountCreationRequest): CreateAccountResponse
    suspend fun getAccount(id: String): Account?
    fun getAccounts(): Flow<List<Account>>
    suspend fun getAccountTransactions(id: String): AccountTransactions?
    suspend fun deleteAccount(id: String)
    suspend fun saveAccount(account: Account)
    suspend fun updateAccountBalance(balance: BigDecimal, id: String)
}