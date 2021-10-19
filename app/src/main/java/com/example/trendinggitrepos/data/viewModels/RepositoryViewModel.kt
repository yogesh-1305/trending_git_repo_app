package com.example.trendinggitrepos.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggitrepos.data.model.RepoApiResponseItem
import com.example.trendinggitrepos.data.model.test.ApiResponse
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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositories.postValue(repository.getRepositories())
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun handleBreakingRepoResponse(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}