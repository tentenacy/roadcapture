package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentSearchBinding
import com.untilled.roadcapture.utils.hideKeyboard
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

        binding.viewpagerSearch.adapter = SearchPagerAdapter(this)

        TabLayoutMediator(binding.tabSearch, binding.viewpagerSearch) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.edtSearchInput.apply {
            setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus) {
                    (requireActivity() as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                } else {
                    (requireActivity() as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
            }
            setOnEditorActionListener { v, actionId, event ->
                when(actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        requireActivity().hideKeyboard(this)
                        return@setOnEditorActionListener true
                    }
                    else -> return@setOnEditorActionListener false
                }
            }
        }
    }

    private fun getTabTitle(position: Int): String? = when(position) {
        TITLE_CRITERIA_PAGE_INDEX -> getString(R.string.search_title_criteria)
        USERNAME_CRITERIA_PAGE_INDEX -> getString(R.string.search_username_criteria)
        else -> null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}