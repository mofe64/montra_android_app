package com.nubari.montra.data.remote

import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.data.remote.responses.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MontraApi {

    @POST("register")
    suspend fun register(@Body request: RegistrationRequest): RegistrationResponse
    @POST("login")
    suspend fun login()
    @GET("verify/{id}/{token}")
    suspend fun verify(
        @Path("id") id: String,
        @Path("token") token: String
    )
}