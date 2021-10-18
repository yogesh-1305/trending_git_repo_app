package com.example.trendinggitrepos.data.api

import com.example.trendinggitrepos.data.model.Repository
import com.example.trendinggitrepos.data.model.RepositoryItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {

    @GET("/repositories")
    suspend fun getRepositories(
        @Query("language")
        pLanguage: String = "java",
        @Query("since")
        since: String = "daily",
        @Query("spoken_language_code")
        languageCode: String
    ): Response<List<RepositoryItem>>
}