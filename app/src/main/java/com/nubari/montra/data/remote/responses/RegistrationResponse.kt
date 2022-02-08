package com.nubari.montra.data.remote.responses

data class RegistrationResponse(
    val success: Boolean,
    val user: User,
    val verificationCode: String
)