package com.nubari.montra.data.remote.responses

data class CreateAccountResponse(
    val success: Boolean,
    val id: String,
    val balance: String
)