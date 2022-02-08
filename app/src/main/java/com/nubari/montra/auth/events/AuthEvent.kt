package com.nubari.montra.auth.events

sealed class AuthEvent {
    data class Login(
        val email: String,
        val password: String
    ) : AuthEvent()

    data class Register(
        val name: String,
        val email: String,
        val password: String
    ) : AuthEvent()

    data class VerifyEmail(
        val verificationCode: String
    ) : AuthEvent()

    data class FailedRegistration(
        val errorMessage: String
    ) : AuthEvent()

    data class SuccessfulRegistration(val message: String) : AuthEvent()
    object SuccessfulVerification : AuthEvent()


}