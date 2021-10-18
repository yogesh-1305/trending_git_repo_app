package com.example.trendinggitrepos.data.api

import com.example.trendinggitrepos.data.model.RepositoryItem
import com.example.trendinggitrepos.data.model.test.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoryApi {

    @GET("/repo")
    suspend fun getRepositories(
        @Query("language")
        pLanguage: String = "java",
        @Query("since")
        since: String = "daily",
        @Query("spoken_language_code")
        languageCode: String = "en"
    ): Response<ApiResponse>
}