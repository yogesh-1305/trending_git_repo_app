package com.example.trendinggitrepos.di

import android.content.Context
import androidx.room.Room
import com.example.trendinggitrepos.constants.Constants.API_BASE_URL
import com.example.trendinggitrepos.constants.Constants.DATABASE_NAME
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.repositories.RepoRepository
import com.example.trendinggitrepos.db.AppDatabase
import com.example.trendinggitrepos.db.DatabaseDao
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

    @Singleton
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDatabaseDao(db: AppDatabase): DatabaseDao = db.dbDao()

    @Provides
    @Singleton
    fun provideApiInstance(@ApplicationContext context: Context): RepositoryApi {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val myCache = Cache(context.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!) {
                    // network is connected, return data from cache if same request is made within 5 seconds
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else {
                    // network dis-connected, return data from cache
                    // if data is less that 7 days old, otherwise discard
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                }
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RepositoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepoRepository(api: RepositoryApi, dao: DatabaseDao): RepoRepository {
        return RepoRepository(api, dao)
    }

}