package com.example.trendinggitrepos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendinggitrepos.adapters.RepositoryAdapter
import com.example.trendinggitrepos.adapters.StarredItemAdapter
import com.example.trendinggitrepos.constants.Constants
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.FragmentStarredRepoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*

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

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                val item = adapter.differ.currentList[position]

                viewModel.deleteRepo(item)

                Snackbar.make(view ,"Successfully Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveRepoToDB(item)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvStarredRepos)
        }

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