package com.untilled.roadcapture.features.root.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentUsernameSearchBinding
import com.untilled.roadcapture.features.common.CustomDivider

class UsernameSearchFragment : Fragment() {
    private var _binding: FragmentUsernameSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsernameSearchBinding.inflate(layoutInflater, container, false)

        initAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {

        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))

        binding.recyclerUsernamesearch.addItemDecoration(customDivider)
    }
}