package com.nubari.montra.auth.events

sealed class AuthEvent {}

sealed class AuthUIEvent : AuthEvent() {
    data class Login(
        val email: String,
        val password: String
    ) : AuthUIEvent()

    data class Register(
        val name: String,
        val email: String,
        val password: String
    ) : AuthEvent()

    data class VerifyEmail(
        val id: String,
        val verificationCode: String
    ) : AuthEvent()
}

sealed class AuthProcessEvent : AuthEvent() {
    object SuccessfulRegistration : AuthProcessEvent()
    object SuccessfulVerification : AuthProcessEvent()
    object SuccessfulLogin : AuthProcessEvent()
    data class FailedRegistration(
        val errorMessage: String
    ) : AuthProcessEvent()

    data class FailedVerification(
        val errorMessage: String
    ) : AuthProcessEvent()

    data class FailedLogin(
        val errorMessage: String
    ) : AuthProcessEvent()
}