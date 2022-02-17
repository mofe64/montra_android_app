package com.nubari.montra.data.remote.requests

data class AccountCreationRequest(
    val id: String,
    val name: String,
    val balance: String,
    val userId: String
)
