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
        pLanguage: String = "",
        @Query("since")
        since: String = ""
    ): Response<ApiResponse>
}