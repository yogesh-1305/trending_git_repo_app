package com.example.trendinggitrepos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trendinggitrepos.R
import com.example.trendinggitrepos.databinding.FragmentRepoListBinding

class RepoListFragment : Fragment() {

    private lateinit var binding: FragmentRepoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoListBinding.inflate(inflater, container, false)
        return binding.root
    }

}