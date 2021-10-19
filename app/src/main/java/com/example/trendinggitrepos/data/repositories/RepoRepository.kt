package com.example.trendinggitrepos.data.repositories

import androidx.lifecycle.LiveData
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.model.CustomRepository
import com.example.trendinggitrepos.db.DatabaseDao
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val api: RepositoryApi,
    private val dao: DatabaseDao
) {

    // ---------- variables ---------------------------------------------------------------

    val readAllRepositories: LiveData<List<CustomRepository>> = dao.getRepositories()

    //------------ methods (API) -----------------------------------------------------------------

    suspend fun getRepositories() = api.getRepositories()

    suspend fun getRepoByLanguage(language: String) = api.getRepoByLang(language)

    //------------ methods (DATABASE) ------------------------------------------------------------

    suspend fun insertRepo(repo: CustomRepository) = dao.insertRepo(repo)
}