package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentUsernameCriteriaSearchBinding
import dagger.hilt.android.AndroidEntryPoint

class UsernameCriteriaSearchFragment : Fragment() {
    private var _binding: FragmentUsernameCriteriaSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsernameCriteriaSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}