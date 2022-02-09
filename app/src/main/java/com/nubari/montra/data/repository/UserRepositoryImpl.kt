package com.nubari.montra.data.repository

import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.remote.requests.LoginRequest
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.AuthResponse
import com.nubari.montra.data.remote.responses.RegistrationResponse
import com.nubari.montra.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: MontraApi
) : UserRepository {
    override suspend fun register(request: RegistrationRequest): RegistrationResponse {
        return api.register(request = request)
    }

    override suspend fun login(request: LoginRequest): AuthResponse {
        return api.login(request = request)
    }

    override suspend fun verify(id: String, token: String):AuthResponse {
        return api.verify(id = id, token = token)
    }


}