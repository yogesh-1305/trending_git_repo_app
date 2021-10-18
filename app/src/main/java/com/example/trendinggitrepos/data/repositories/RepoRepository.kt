package com.example.trendinggitrepos.data.repositories

import com.example.trendinggitrepos.data.api.RepositoryApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val api: RepositoryApi
) {
    suspend fun getRepositories() = api.getRepositories()
}