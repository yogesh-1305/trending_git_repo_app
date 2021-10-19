package com.example.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.data.api.RepositoryApi
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.ActivityMainBinding
import com.example.trendinggitrepos.util.Resource
import com.example.trendinggitrepos.util.UtilityMethods.hide
import com.example.trendinggitrepos.util.startAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RepositoryViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var destinationChangedListener: NavController.OnDestinationChangedListener

    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation = AnimationUtils.loadAnimation(this, R.anim.fab_expolsion_animation).apply {
            duration = 800
            interpolator = AccelerateDecelerateInterpolator()
        }

        navController = findNavController(R.id.fragmentContainerView)
        destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.repoListFragment -> {
                        Log.d("On fragment===", "Home")
                        binding.toolbar.navigationIcon = null
                        binding.fabSearch.show()
                        binding.toolbar.title = "Trending"
                    }
                    R.id.searchFragment -> {
                        Log.d("On fragment===", "Search")
                        binding.toolbar.title = "Search"
                        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

                    }
                }
            }

        setClickListeners()
    }

    private fun setClickListeners() {
        binding.fabSearch.setOnClickListener {
            it.hide()
            navController.navigate(R.id.action_repoListFragment_to_searchFragment)
//            binding.animView.startAnimation(animation) {
//                // after animation ends -> do something
//            }
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
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