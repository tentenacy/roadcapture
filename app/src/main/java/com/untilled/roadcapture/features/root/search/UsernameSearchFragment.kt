package com.untilled.roadcapture.features.root.search

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.untilled.roadcapture.R
import com.untilled.roadcapture.comment
import com.untilled.roadcapture.databinding.FragmentUsernameSearchBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.usernameSearch
import com.untilled.roadcapture.utils.DummyDataSet

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

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerviewUsernameSearch.addItemDecoration(customDivider)

        binding.recyclerviewUsernameSearch.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                usernameSearch {
                    id(index)
                    user(user)
                }
            }
        }
    }

}