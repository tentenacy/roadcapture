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
import com.untilled.roadcapture.databinding.FragmentFollowerBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.follow
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment(){

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)
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
        binding.imageFollowerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.edtFollowerInput.setOnEditorActionListener { v, actionId, event ->
            when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {

                    requireActivity().hideKeyboard(binding.edtFollowerInput)
                    return@setOnEditorActionListener true
                }
                else -> return@setOnEditorActionListener false
            }
        }
    }

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerFollower.addItemDecoration(customDivider)

        binding.recyclerFollower.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                follow {
                    id(index)
                    user(user)

                    onClickItem { model, parentView, clickedView, position ->
                        when(clickedView.id){
                            R.id.image_ifollow_profile -> Navigation.findNavController(binding.root)
                                .navigate(R.id.action_followerFragment_to_studioFragment)
                        }
                    }
                }
            }
        }
    }

}