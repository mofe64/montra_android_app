package com.nubari.montra.data.repository

import com.nubari.montra.data.local.dao.AccountDao
import com.nubari.montra.data.local.models.Account
import com.nubari.montra.data.local.models.AccountTransactions
import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.remote.requests.AccountCreationRequest

import com.nubari.montra.data.remote.responses.CreateAccountResponse
import com.nubari.montra.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
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
        accountDao.insert(account = account)
        return response
    }

    override suspend fun saveAccount(account: Account) {
        accountDao.insert(account = account)
    }

    override suspend fun getAccount(id: String): Account? {
        return accountDao.getAccount(id = id)
    }

    override fun getAccounts(): Flow<List<Account>> {
        return accountDao.getMyAccounts()
    }

    override fun getAccountTransactions(id: String): Flow<List<AccountTransactions>> {
        return accountDao.getAccountTransactions(id = id)
    }

    override suspend fun deleteAccount(id: String) {
        val account = accountDao.getAccount(id)
        account?.let {
            accountDao.deleteAccount(account = it)
        }
    }


}