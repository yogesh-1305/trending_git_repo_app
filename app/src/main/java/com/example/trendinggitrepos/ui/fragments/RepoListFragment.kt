package com.example.trendinggitrepos.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trendinggitrepos.adapters.RepositoryAdapter
import com.example.trendinggitrepos.constants.Constants.REPO_LIST_FRAGMENT_ID
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.FragmentRepoListBinding
import com.example.trendinggitrepos.util.Resource
import com.example.trendinggitrepos.util.UtilityMethods.hide
import com.example.trendinggitrepos.util.UtilityMethods.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private lateinit var binding: FragmentRepoListBinding
    private lateinit var adapter: RepositoryAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val viewModel: RepositoryViewModel by viewModels()
    var refreshed = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        setupRecyclerView()

        swipeRefreshLayout = binding.swipeRefLayout

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getRepositories()
            refreshed = true
        }

        viewModel.getRepositories()
        viewModel.repositories.observe(viewLifecycleOwner, {

            it?.let {
                if (refreshed) {
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.submitList(it)

                }
                Log.i("list ---", it.toString())
                binding.shimmerRepoList.hide()
                binding.rvRepoList.show()
                swipeRefreshLayout.isRefreshing = false
            }
        })

        return binding.root
    }

    private fun setupRecyclerView() = binding.rvRepoList.apply {
        this@RepoListFragment.adapter = RepositoryAdapter(context, REPO_LIST_FRAGMENT_ID)
        this.adapter = this@RepoListFragment.adapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}