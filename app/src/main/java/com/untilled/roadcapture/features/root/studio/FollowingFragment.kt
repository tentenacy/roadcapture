package com.untilled.roadcapture.features.root.studio

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.user.User
import com.untilled.roadcapture.databinding.FragmentFollowingBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.follow
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import com.untilled.roadcapture.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment(){

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val args: FollowerFragmentArgs by navArgs()
    private val viewModel: FollowViewModel by viewModels()
    private val userObserver = { user: PageResponse<UsersResponse> ->
        initAdapter(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDivider()
        initViews()
        observeData()
        setOnClickListeners()
    }
    private fun initViews(){
        viewModel.getUserFollowing(FollowingsCondition(args.id), PageRequest())
    }
    private fun observeData(){
        viewModel.user.observe(viewLifecycleOwner,userObserver)
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

    private fun initAdapter(user: PageResponse<UsersResponse>){
        binding.recyclerFollowing.withModels { initFollowingItems(user) }
    }

    private fun EpoxyController.initFollowingItems(user: PageResponse<UsersResponse>) {
        user.content.forEachIndexed { index, user ->
            follow {
                id(index)
                user(user)

                onClickItem { model, parentView, clickedView, position ->
                    when (clickedView.id) {
                        R.id.img_ifollow_profile -> Navigation.findNavController(binding.root)
                            .navigate(R.id.action_followingFragment_to_studioFragment)
                    }
                }
            }
        }
    }

    private fun initDivider() {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recyclerFollowing.addItemDecoration(customDivider)
    }

}