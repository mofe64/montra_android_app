package com.nubari.montra.di

import android.app.Application
import androidx.room.Room
import com.nubari.montra.data.local.MontraDatabase
import com.nubari.montra.data.remote.AuthInterceptor
import com.nubari.montra.data.remote.MontraApi
import com.nubari.montra.data.repository.AccountRepositoryImpl
import com.nubari.montra.data.repository.CategoryRepositoryImpl
import com.nubari.montra.data.repository.TransactionRepositoryImpl
import com.nubari.montra.data.repository.UserRepositoryImpl
import com.nubari.montra.domain.repository.AccountRepository
import com.nubari.montra.domain.repository.CategoryRepository
import com.nubari.montra.domain.repository.TransactionRepository
import com.nubari.montra.domain.repository.UserRepository
import com.nubari.montra.domain.usecases.account.GetAccountTransactions
import com.nubari.montra.domain.usecases.account.AccountUseCases
import com.nubari.montra.domain.usecases.account.CreateAccount
import com.nubari.montra.domain.usecases.account.GetAccount
import com.nubari.montra.domain.usecases.account.GetAllAccounts
import com.nubari.montra.domain.usecases.auth.AuthenticationUseCases
import com.nubari.montra.domain.usecases.auth.Login
import com.nubari.montra.domain.usecases.auth.Register
import com.nubari.montra.domain.usecases.auth.VerifyEmail
import com.nubari.montra.domain.usecases.category.CategoryUseCases
import com.nubari.montra.domain.usecases.category.GetAllCategories
import com.nubari.montra.domain.usecases.transaction.CreateTransaction
import com.nubari.montra.domain.usecases.transaction.TransactionUseCases
import com.nubari.montra.general.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMontraDatabase(app: Application): MontraDatabase {
        return Room.databaseBuilder(
            app,
            MontraDatabase::class.java,
            MontraDatabase.DATABASE_NAME
        )
            .createFromAsset("database/Montra.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideMontraApi(client: OkHttpClient): MontraApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(MontraApi::class.java)

    }

    @Provides
    @Singleton
    fun provideUserRepository(api: MontraApi): UserRepository {
        return UserRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(api: MontraApi, db: MontraDatabase): AccountRepository {
        return AccountRepositoryImpl(api = api, accountDao = db.accountDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: MontraDatabase): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao = db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(db: MontraDatabase): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao = db.transactionDao)
    }

    @Provides
    @Singleton
    fun provideAuthenticationUseCases(repository: UserRepository): AuthenticationUseCases {
        return AuthenticationUseCases(
            register = Register(repository = repository),
            login = Login(repository = repository),
            verifyEmail = VerifyEmail(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideAccountUseCases(repository: AccountRepository): AccountUseCases {
        return AccountUseCases(
            createAccount = CreateAccount(repository = repository),
            getAccount = GetAccount(repository = repository),
            getAllAccounts = GetAllAccounts(repository = repository),
            getAccountTransactions = GetAccountTransactions(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getAllCategories = GetAllCategories(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideTransactionUseCases(
        repository: TransactionRepository,
        accountRepository: AccountRepository
    ): TransactionUseCases {
        return TransactionUseCases(
            createTransaction = CreateTransaction(
                repository = repository,
                accountRepository = accountRepository
            )
        )
    }
}