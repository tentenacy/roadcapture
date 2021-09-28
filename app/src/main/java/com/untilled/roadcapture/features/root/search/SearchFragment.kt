package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        val tabLayout = binding.tablayoutSearch
        val viewPager = binding.viewpagerSearch

        viewPager.adapter = SearchPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        Log.d("Tag", arguments.toString())
        if (arguments != null)
        {
            binding.edittextSearchInput.setText(requireArguments().getString("searchTitle"))
        }

        return binding.root
    }

    private fun getTabTitle(position: Int): String? = when(position) {
        TITLE_CRITERIA_PAGE_INDEX -> getString(R.string.search_title_criteria)
        USERNAME_CRITERIA_PAGE_INDEX -> getString(R.string.search_username_criteria)
        PLACE_CRITERIA_PAGE_INDEX -> getString(R.string.search_place_criteria)
        else -> null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}