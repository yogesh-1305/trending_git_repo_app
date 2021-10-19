package com.example.trendinggitrepos.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.adapters.RepositoryAdapter
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), TextView.OnEditorActionListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: RepositoryAdapter
    private val viewModel: RepositoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupRecyclerView()

        viewModel.searchResults.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
                Log.i("search repo ---", it.toString())
            }
        })

        binding.etSearch.setOnEditorActionListener(this)
        return binding.root
    }

    private fun setupRecyclerView() = binding.rvSearch.apply {
        this@SearchFragment.adapter = RepositoryAdapter(context)
        this.adapter = this@SearchFragment.adapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val typedText = binding.etSearch.text

            viewModel.getRepoByLanguage(typedText.toString())
            return true
        }
        return true
    }
}