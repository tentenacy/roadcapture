package com.untilled.roadcapture.features.follower

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.databinding.FragmentFollowerBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment(){

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowerViewModel by viewModels()
    private val args: FollowerFragmentArgs by navArgs()
    private val userObserver: (PagingData<Followers.Follower>) -> Unit = { pagingData ->

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

    private fun initViews() {
        viewModel.getUserFollower(args.id.toLong())
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner, userObserver)
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
    }

    private fun initDivider() {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recyclerFollower.addItemDecoration(customDivider)
    }

}