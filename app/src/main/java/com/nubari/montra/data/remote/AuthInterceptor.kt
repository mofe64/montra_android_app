package com.nubari.montra.data.remote

import com.nubari.montra.preferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (preferences.authenticationToken.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer ${preferences.authenticationToken}")
        }
        return chain.proceed(requestBuilder.build())
    }


}