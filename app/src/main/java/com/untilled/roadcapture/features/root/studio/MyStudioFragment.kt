package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.token.Token
import com.untilled.roadcapture.data.entity.user.User
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.placeFilter
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMystudioBinding? = null
    val binding get() = _binding!!

    private val viewModel: StudioViewModel by viewModels()

    private val userObserver = { user: UsersResponse ->
        binding.user = user
    }
    private val followerObserver = { follower: PageResponse<UsersResponse> ->
        binding.follower = follower
    }
    private val followingObserver = { following: PageResponse<UsersResponse> ->
        binding.following = following
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMystudioBinding.inflate(layoutInflater, container, false)

        initAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViews()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner,userObserver)
        viewModel.follower.observe(viewLifecycleOwner,followerObserver)
        viewModel.following.observe(viewLifecycleOwner,followingObserver)
    }

    private fun initViews(){
        viewModel.getUserInfo(User.id)
        viewModel.getUserFollower(FollowingsCondition(User.id), PageRequest())
        viewModel.getUserFollowing(FollowingsCondition(User.id), PageRequest())
    }

    private fun setOnClickListeners() {
        binding.textMystudioFollower.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToFollowerFragment(User.id))
        }
        binding.textMystudioFollowing.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToFollowingFragment(User.id))
        }
        binding.btnMystudioEdit.setOnClickListener {
//            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
//                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user))
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
    }

    private fun initAdapter() {
//        binding.recyclerMystudioAlbum.withModels {
//            DummyDataSet.studios.forEachIndexed { index, album ->
//                studioAlbum {
//                    id(index)
//                    studio(album)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when(clickedView.id){
//                            R.id.image_ialbums_studio_thumbnail ->
//                                Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
//                                navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment(model.studio().id))
//                        }
//                    }
//                }
//            }
//        }
        binding.recyclerMystudioPlace.withModels {
            DummyDataSet.places.forEachIndexed { index, place ->
                placeFilter {
                    id(index)
                    place(place)

                    onClickItem { model, parentView, clickedView, position ->
                        when(clickedView.id){
                            R.id.view_iplace_filter_overlay ->
                                clickedView.isSelected = !clickedView.isSelected
                        }
                    }
                }
            }
        }
    }
}