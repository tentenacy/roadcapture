package com.untilled.roadcapture.features.root.search

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentUsernameSearchBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.usernameSearch
import com.untilled.roadcapture.utils.dummy.DummyDataSet

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

        binding.recyclerUsernamesearch.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                usernameSearch {
                    id(index)
                    user(user)

                    onClickItem { model, parentView, clickedView, position ->
                        when (clickedView.id) {
                            R.id.img_iusernamesearch_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root                            )
                                .navigate(R.id.action_rootFragment_to_studioFragment)
                        }
                    }
                }
            }
        }
    }
}