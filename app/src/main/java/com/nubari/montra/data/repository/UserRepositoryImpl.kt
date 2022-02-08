package com.nubari.montra.data.repository

import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.RegistrationResponse
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.general.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepositoryImpl @Inject constructor(
    private val api: MontraApi
) : UserRepository {
    override suspend fun register(request: RegistrationRequest): Resource<RegistrationResponse> {
        val response = try {
            api.register(request = request)
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Something went wrong")
        }
        return Resource.Success(response)
    }

    override suspend fun login() {
        TODO("Not yet implemented")
    }

    override suspend fun verify(id: String, token: String) {
        TODO("Not yet implemented")
    }
}