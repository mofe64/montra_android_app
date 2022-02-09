package com.nubari.montra.data.remote.responses

data class AuthResponse(
    val user: User,
    val status: String,
    val token: String
)