package com.example.trendinggitrepos.di

import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.repositories.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiInstance(): RepositoryApi {

        return Retrofit.Builder()
            .baseUrl("https://trendings.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RepositoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepoRepository(api: RepositoryApi): RepoRepository {
        return RepoRepository(api)
    }

}