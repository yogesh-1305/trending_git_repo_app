package com.example.trendinggitrepos.data.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggitrepos.data.model.RepoApiResponseItem
import com.example.trendinggitrepos.data.model.CustomRepository
import com.example.trendinggitrepos.data.repositories.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepoRepository
) : ViewModel() {

    // ---------- variables ---------------------------------------------------------------

    val repositories: MutableLiveData<List<RepoApiResponseItem>> by lazy {
        MutableLiveData()
    }

    val searchResults: MutableLiveData<List<RepoApiResponseItem>?> by lazy {
        MutableLiveData()
    }

    val readAllRepositories: LiveData<List<CustomRepository>> = repository.readAllRepositories


    //------------ methods (API) -----------------------------------------------------------------

    fun getRepositories() {
        viewModelScope.launch {
            try {
                val response = repository.getRepositories()
                setValue(response)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun setValue(response: List<RepoApiResponseItem>) {
        repositories.value = response
    }

    fun getRepoByLanguage(language: String) {
        viewModelScope.launch {
            try {
                val response = repository.getRepoByLanguage(language)
                searchResults.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
                searchResults.postValue(null)
            }
        }
    }

    //------------ methods (DB) -----------------------------------------------------------------

    fun saveRepoToDB(repo: CustomRepository) {
        viewModelScope.launch {
            repository.insertRepo(repo)
        }
    }
}