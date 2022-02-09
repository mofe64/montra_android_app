package com.nubari.montra.domain.usecases.authUsecases

data class AuthenticationUseCases(
    val registerUseCase: RegisterUseCase,
    val loginUseCase: LoginUseCase,
    val verifyEmailUseCase: VerifyEmailUseCase
)