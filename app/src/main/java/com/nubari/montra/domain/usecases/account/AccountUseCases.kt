package com.nubari.montra.domain.usecases.account

data class AccountUseCases(
    val createAccount: CreateAccount,
    val getAccount: GetAccount,
    val getAllAccounts: GetAllAccounts,
    val getAccountTransactions: GetAccountTransactions,
)