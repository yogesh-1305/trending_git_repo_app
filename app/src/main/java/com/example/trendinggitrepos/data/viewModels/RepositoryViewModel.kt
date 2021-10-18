package com.example.trendinggitrepos.data.viewModels

import androidx.lifecycle.ViewModel
import com.example.trendinggitrepos.data.repositories.RepoRepository
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(
    val repository: RepoRepository
) : ViewModel() {
}