package com.example.trendinggitrepos.data.api

import com.example.trendinggitrepos.data.model.RepoApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryApi {

    @GET("/repositories")
    suspend fun getRepositories(): List<RepoApiResponseItem>

    @GET("/repositories/{language}")
    suspend fun getRepoByLang(@Path("language") language: String?): List<RepoApiResponseItem>
}