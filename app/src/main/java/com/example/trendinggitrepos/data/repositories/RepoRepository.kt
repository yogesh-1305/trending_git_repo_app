package com.example.trendinggitrepos.data.repositories

import androidx.lifecycle.LiveData
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.model.entity.CustomRepository
import com.example.trendinggitrepos.db.DatabaseDao
import dagger.hilt.android.AndroidEntryPoint
import java.security.PrivateKey
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