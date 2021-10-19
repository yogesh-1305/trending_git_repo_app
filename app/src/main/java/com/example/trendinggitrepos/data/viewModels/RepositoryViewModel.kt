package com.example.trendinggitrepos.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggitrepos.data.model.RepoApiResponseItem
import com.example.trendinggitrepos.data.repositories.RepoRepository
import com.example.trendinggitrepos.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepoRepository
) : ViewModel() {

    val repositories: MutableLiveData<List<RepoApiResponseItem>> by lazy {
        MutableLiveData()
    }

    var loadedOnce = false

    fun getRepositories() {
        viewModelScope.launch {
            try {
                val response = repository.getRepositories()
                setValue(response)

            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun setValue(response: List<RepoApiResponseItem>) {
        repositories.value = response
    }

}