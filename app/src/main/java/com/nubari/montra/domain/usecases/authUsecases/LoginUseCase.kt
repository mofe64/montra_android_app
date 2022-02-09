package com.nubari.montra.domain.usecases.authUsecases

import android.util.Log
import com.nubari.montra.data.remote.requests.LoginRequest
import com.nubari.montra.data.remote.responses.AuthResponse
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.general.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.lang.Exception

class LoginUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(request: LoginRequest): Flow<Resource<AuthResponse>> =
        flow {
            try {
                Log.i("login", "flow try")
                emit(Resource.Loading<AuthResponse>())
                val response = repository.login(request = request)
                Log.i("login", response.toString())
                emit(Resource.Success<AuthResponse>(response))
            } catch (e: HttpException) {
                Log.i("login", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.i("login", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        "Couldn't reach server. Check your internet connection."
                    )
                )
            } catch (e: Exception) {
                Log.i("login", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            }
        }
}