package com.untilled.roadcapture.features.root.studio

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.untilled.roadcapture.databinding.FragmentFollowerBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.follow
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import com.untilled.roadcapture.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment(){

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowViewModel by viewModels()
    private val args: FollowerFragmentArgs by navArgs()
    private val userObserver = { user: PageResponse<UsersResponse> ->
        initAdapter(user)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)

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
        viewModel.getUserFollower(FollowingsCondition(args.id), PageRequest())
    }
    private fun observeData(){
        viewModel.user.observe(viewLifecycleOwner,userObserver)
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

    private fun initAdapter(user: PageResponse<UsersResponse>){
        binding.recyclerFollower.withModels { initFollowerItems(user) }
    }

    private fun EpoxyController.initFollowerItems(user: PageResponse<UsersResponse>) {
        user.content.forEachIndexed { index, user ->
            follow {
                id(index)
                user(user)

                onClickItem { model, parentView, clickedView, position ->
                    when (clickedView.id) {
                        R.id.img_ifollow_profile -> Navigation.findNavController(binding.root)
                            .navigate(FollowerFragmentDirections.actionFollowerFragmentToStudioFragment(model.user().id))
                        R.id.btn_ifollow -> viewModel.follow(model.user().id)
                    }
                }
            }
        }
    }

    private fun initDivider() {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recyclerFollower.addItemDecoration(customDivider)
    }

}