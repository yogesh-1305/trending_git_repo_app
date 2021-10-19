package com.example.trendinggitrepos.di

import android.content.Context
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.repositories.RepoRepository
import com.example.trendinggitrepos.util.UtilityMethods.hasNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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
    fun provideApiInstance(@ApplicationContext context: Context): RepositoryApi {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .build()
        return Retrofit.Builder()
            .baseUrl("https://gh-trending-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RepositoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepoRepository(api: RepositoryApi): RepoRepository {
        return RepoRepository(api)
    }

}