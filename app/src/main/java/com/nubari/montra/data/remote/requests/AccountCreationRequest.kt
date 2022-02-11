package com.nubari.montra.data.remote.requests

data class AccountCreationRequest(
    val name: String,
    val balance: String,
    val userId: String
)
