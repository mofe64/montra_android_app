package com.nubari.montra.domain.usecases.auth

import android.util.Log
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.RegistrationResponse
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.general.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class Register(
    private val repository: UserRepository
) {
    operator fun invoke(request: RegistrationRequest): Flow<Resource<RegistrationResponse>> =
        flow {
            try {
                Log.i("register", "flow try")
                emit(Resource.Loading<RegistrationResponse>())
                val response = repository.register(request = request)
                Log.i("register", response.toString())
                emit(Resource.Success<RegistrationResponse>(response))
            } catch (e: HttpException) {
                Log.i("register", "flow catch $e")
                emit(
                    Resource.Error<RegistrationResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.i("register", "flow catch $e")
                emit(Resource.Error<RegistrationResponse>("Couldn't reach server. Check your internet connection."))
            } catch (e: Exception) {
                Log.i("register", "flow catch $e")
                emit(
                    Resource.Error<RegistrationResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            }
        }
}