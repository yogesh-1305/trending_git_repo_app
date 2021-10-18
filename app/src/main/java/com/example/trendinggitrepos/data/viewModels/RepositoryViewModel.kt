package com.example.trendinggitrepos.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendinggitrepos.data.model.RepositoryItem
import com.example.trendinggitrepos.data.model.test.ApiResponse
import com.example.trendinggitrepos.data.repositories.RepoRepository
import com.example.trendinggitrepos.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repository: RepoRepository
) : ViewModel() {

    val repositories: MutableLiveData<Resource<ApiResponse>> by lazy {
        MutableLiveData()
    }

    fun getRepositories() {
        viewModelScope.launch(Dispatchers.IO) {
            repositories.postValue(Resource.Loading())
            try {
                val response = repository.getRepositories()
                repositories.postValue(handleBreakingRepoResponse(response))
            } catch (e: Exception) {
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