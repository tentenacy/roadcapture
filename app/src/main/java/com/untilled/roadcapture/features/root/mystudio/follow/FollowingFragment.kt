package com.untilled.roadcapture.features.root.mystudio.follow

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentFollowingBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.follow
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment(){

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)
        initAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.imageviewFollowingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.edittextFollowingSearchInput.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {

                    requireActivity().hideKeyboard(binding.edittextFollowingSearchInput)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerviewFollowing.addItemDecoration(customDivider)

        binding.recyclerviewFollowing.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                follow {
                    id(index)
                    user(user)
                }
            }
        }
    }

}