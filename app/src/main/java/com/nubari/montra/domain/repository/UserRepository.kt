package com.nubari.montra.domain.repository

import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.RegistrationResponse
import com.nubari.montra.general.util.Resource


interface UserRepository {
    suspend fun register(request: RegistrationRequest): Resource<RegistrationResponse>
    suspend fun login()
    suspend fun verify(id: String, token: String)
}