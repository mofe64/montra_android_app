package com.nubari.montra.domain.usecases.auth

data class AuthenticationUseCases(
    val register: Register,
    val login: Login,
    val verifyEmail: VerifyEmail
)