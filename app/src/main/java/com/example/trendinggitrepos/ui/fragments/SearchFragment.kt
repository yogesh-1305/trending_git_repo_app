package com.example.trendinggitrepos.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.adapters.RepositoryAdapter
import com.example.trendinggitrepos.constants.Constants.SEARCH_LIST_FRAGMENT_ID
import com.example.trendinggitrepos.data.viewModels.RepositoryViewModel
import com.example.trendinggitrepos.databinding.FragmentSearchBinding
import com.example.trendinggitrepos.util.UtilityMethods.hide
import com.example.trendinggitrepos.util.UtilityMethods.show
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
            Log.i("check data ===", it.toString())
            binding.searchProgressIndicator.hide()
            if (it != null) {
                adapter.submitList(it)
                showDataInRecyclerview()
            } else {
               showAnimationForNoDataFound()
            }
        })

        binding.etSearch.setOnEditorActionListener(this)
        return binding.root
    }

    private fun setupRecyclerView() = binding.rvSearch.apply {
        this@SearchFragment.adapter = RepositoryAdapter(context, SEARCH_LIST_FRAGMENT_ID)
        this.adapter = this@SearchFragment.adapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            binding.searchProgressIndicator.show()
            val typedText = binding.etSearch.text
            viewModel.getRepoByLanguage(typedText.toString())
            return true
        }
        return true
    }

    private fun showDataInRecyclerview() {
        binding.lottieAnimView.hide()
        binding.rvSearch.show()
    }
    private fun showAnimationForNoDataFound() {
        binding.lottieAnimView.apply {
            setAnimation(R.raw.lottie_amin_no_data_found)
            this.playAnimation()
        }
        binding.lottieAnimView.show()
        binding.rvSearch.hide()
    }

}