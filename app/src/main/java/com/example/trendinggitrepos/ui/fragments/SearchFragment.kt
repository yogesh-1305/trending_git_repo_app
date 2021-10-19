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
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), TextView.OnEditorActionListener {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.etSearch.addTextChangedListener {

        }

        binding.etSearch.setOnEditorActionListener(this)
        return binding.root
    }

    override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            Log.i("seatch tap ---", "tapp")
            return true
        }
        return true
    }
}