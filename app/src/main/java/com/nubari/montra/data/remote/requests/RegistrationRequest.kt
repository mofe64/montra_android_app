package com.nubari.montra.data.remote.requests

data class RegistrationRequest(
    val name: String,
    val email: String,
    val password: String
)
