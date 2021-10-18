package com.example.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.ActivityMainBinding
import com.example.trendinggitrepos.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getRepositories()
        viewModel.repositories.observe(this, {
            Log.i("tag response ===", it.toString())
            when (it) {
                is Resource.Success -> {
                    Log.i("response body ===", it.data.toString())
                }
            }
        })
    }
}