package com.nubari.montra.domain.usecases.account

import android.util.Log
import com.nubari.montra.data.remote.requests.AccountCreationRequest
import com.nubari.montra.data.remote.responses.CreateAccountResponse
import com.nubari.montra.domain.repository.AccountRepository
import com.nubari.montra.general.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class CreateAccount(
    private val repository: AccountRepository
) {
    operator fun invoke(request: AccountCreationRequest): Flow<Resource<CreateAccountResponse>> =
        flow {
            try {
                emit(Resource.Loading<CreateAccountResponse>())
                val response = repository.createAccount(request = request)
                emit(Resource.Success<CreateAccountResponse>(response))
            } catch (e: HttpException) {
                Log.i("create-account", "code : ${e.code()}")
                Log.i("create-account", "response : ${e.response().toString()}")
                emit(
                    Resource.Error<CreateAccountResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            } catch (e: IOException) {
                Log.i("create-account", "catch ioexception: $e")
                emit(
                    Resource.Error<CreateAccountResponse>(
                        "Couldn't reach server. Check your internet connection."
                    )
                )

            } catch (e: Exception) {
                Log.i("create-account", "catch exception: $e")
                emit(
                    Resource.Error<CreateAccountResponse>(
                        e.localizedMessage ?: "An unexpected error occurred"
                    )
                )
            }
        }
}