package com.nubari.montra.di

import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.repository.UserRepositoryImpl
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.general.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMontraApi(): MontraApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MontraApi::class.java)

    }

    @Provides
    @Singleton
    fun provideUserRepository(api: MontraApi): UserRepository {
        return UserRepositoryImpl(api = api)
    }
}