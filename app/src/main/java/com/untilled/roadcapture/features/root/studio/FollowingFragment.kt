package com.untilled.roadcapture.features.root.studio

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
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
        binding.imageFollowingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.edtFollowingInput.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    requireActivity().hideKeyboard(binding.edtFollowingInput)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerFollowing.addItemDecoration(customDivider)

        binding.recyclerFollowing.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                follow {
                    id(index)
                    user(user)

                    onClickItem { model, parentView, clickedView, position ->
                        when(clickedView.id){
                            R.id.img_ifollow_profile -> Navigation.findNavController(binding.root)
                                .navigate(R.id.action_followingFragment_to_studioFragment)
                        }
                    }
                }
            }
        }
    }

}