package com.nubari.montra.domain.repository

import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.data.remote.requests.LoginRequest
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.AuthResponse
import com.nubari.montra.data.remote.responses.CreateAccountResponse
import com.nubari.montra.data.remote.responses.RegistrationResponse


interface UserRepository {
    suspend fun register(request: RegistrationRequest): RegistrationResponse
    suspend fun login(request: LoginRequest): AuthResponse
    suspend fun verify(id: String, token: String): AuthResponse

}