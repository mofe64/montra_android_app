package com.nubari.montra.data.remote

import com.nubari.montra.data.remote.requests.LoginRequest
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.AuthResponse
import com.nubari.montra.data.remote.responses.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MontraApi {

    @POST("auth/register")
    suspend fun register(@Body request: RegistrationRequest): RegistrationResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/verify/{id}/{token}")
    suspend fun verify(
        @Path("id") id: String,
        @Path("token") token: String
    ): AuthResponse
}