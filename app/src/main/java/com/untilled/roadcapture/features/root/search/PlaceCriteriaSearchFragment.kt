package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPlaceCriteriaSearchBinding
import dagger.hilt.android.AndroidEntryPoint

class PlaceCriteriaSearchFragment : Fragment() {

    private var _binding: FragmentPlaceCriteriaSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlaceCriteriaSearchBinding.inflate(layoutInflater, container, false)

        Logger.d("화면 전환 시 호출되나?")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}