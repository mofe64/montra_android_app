package com.nubari.montra.domain.usecases.auth

import android.util.Log
import com.nubari.montra.data.remote.responses.AuthResponse
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.general.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import java.lang.Exception

class VerifyEmail(
    private val repository: UserRepository
) {
    operator fun invoke(id: String, token: String): Flow<Resource<AuthResponse>> =
        flow {
            try {
                Log.i("verify-email", "flow try")
                emit(Resource.Loading<AuthResponse>())
                val response = repository.verify(id, token)
                Log.i("verify-email", response.toString())
                emit(Resource.Success<AuthResponse>(response))
            } catch (e: HttpException) {
                Log.i("verify-email", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.i("verify-email", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        "Couldn't reach server. Check your internet connection."
                    )
                )
            } catch (e: Exception) {
                Log.i("verify-email", "flow catch $e")
                emit(
                    Resource.Error<AuthResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            }
        }
}