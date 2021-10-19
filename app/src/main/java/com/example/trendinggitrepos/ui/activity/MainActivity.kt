package com.example.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.trendinggitrepos.R
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

    private lateinit var navController: NavController
    private lateinit var destinationChangedListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView)
        destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.repoListFragment -> {
                        Log.d("On fragment===", "Home")
                    }
                    R.id.searchFragment -> {
                        Log.d("On fragment===", "Search")
                    }
                }
            }

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.fabSearch.setOnClickListener {
            navController.navigate(R.id.action_repoListFragment_to_searchFragment)
        }
    }

    override fun onResume() {
        navController.addOnDestinationChangedListener(destinationChangedListener)
        super.onResume()
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(destinationChangedListener)
        super.onPause()
    }
}