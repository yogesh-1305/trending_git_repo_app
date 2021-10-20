package com.example.trendinggitrepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendinggitrepos.adapters.RepositoryAdapter
import com.example.trendinggitrepos.adapters.StarredItemAdapter
import com.example.trendinggitrepos.constants.Constants
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.FragmentStarredRepoBinding

class StarredRepoFragment : Fragment() {

    private lateinit var binding: FragmentStarredRepoBinding
    private lateinit var adapter: StarredItemAdapter

    private val viewModel: RepositoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStarredRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.readAllRepositories.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.submitList(it)
            }
        })

    }

    private fun setupRecyclerView() = binding.rvStarredRepos.apply {
        this@StarredRepoFragment.adapter = StarredItemAdapter(requireActivity())
        this.adapter = this@StarredRepoFragment.adapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}