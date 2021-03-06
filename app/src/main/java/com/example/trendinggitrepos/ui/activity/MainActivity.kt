package com.example.trendinggitrepos.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
import com.example.trendinggitrepos.ui.fragments.WebViewFragment
import com.example.trendinggitrepos.util.Resource
import com.example.trendinggitrepos.util.UtilityMethods.gone
import com.example.trendinggitrepos.util.UtilityMethods.hide
import com.example.trendinggitrepos.util.startAnimation
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: RepositoryViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var destinationChangedListener: NavController.OnDestinationChangedListener

//    private lateinit var animation: Animation

    private var buttonState: FabState = FabState.ON_REPO_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        animation = AnimationUtils.loadAnimation(this, R.anim.fab_expolsion_animation).apply {
//            duration = 800
//            interpolator = AccelerateDecelerateInterpolator()
//        }

        navController = findNavController(R.id.fragmentContainerView)
        destinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.repoListFragment -> {
                        alterViewsForRepoListFragment()
                    }
                    R.id.searchFragment -> {
                        alterViewsForSearchFragment()
                    }
                    R.id.webViewFragment -> {
                        alterViewsForWebViewFragment()
                    }
                    R.id.starredRepoFragment -> {
                        alterViewsForStarredRepoFragment()
                    }
                }
            }

        setClickListeners()
    }


    private fun alterViewsForRepoListFragment() {
        buttonState = FabState.ON_REPO_FRAGMENT
        binding.fabSearch.apply {
            setImageResource(R.drawable.ic_baseline_search_24)
            show()
        }
        binding.toolbar.apply {
            navigationIcon = null
            title = "Trending"
        }
    }

    private fun alterViewsForSearchFragment() {
        binding.fabSearch.gone()
        binding.toolbar.apply {
            title = "Search"
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    private fun alterViewsForWebViewFragment() {
        buttonState = FabState.ON_WEB_VIEW_FRAGMENT
        binding.fabSearch.hide()
        binding.toolbar.apply {
            title = "Repository Web"
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    private fun alterViewsForStarredRepoFragment() {
        binding.fabSearch.hide()
        binding.toolbar.apply {
            title = "Starred Repositories"
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        }
    }

    private fun setClickListeners() {
        binding.fabSearch.setOnClickListener {
            when (buttonState) {
                FabState.ON_REPO_FRAGMENT -> {
                    it.hide()
                    navController.navigate(R.id.action_repoListFragment_to_searchFragment)
//            binding.animView.startAnimation(animation) {
//                // after animation ends -> do something
//            }
                }
                FabState.ON_WEB_VIEW_FRAGMENT -> {
                    val repo = WebViewFragment.getRepoToSave()
                    viewModel.saveRepoToDB(repo)
                    Snackbar.make(binding.root, "Repository Starred Success", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_saved_repos -> {
                    navController.navigate(R.id.action_global_starredRepoFragment)
                }
            }
            true
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

enum class FabState {
    ON_REPO_FRAGMENT,
    ON_WEB_VIEW_FRAGMENT
}