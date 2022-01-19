package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.*
import com.untilled.roadcapture.data.entity.token.Token
import com.untilled.roadcapture.databinding.FragmentStudioBinding
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioFragment : Fragment() {

    private var _binding: FragmentStudioBinding? = null
    val binding get() = _binding!!

    val args: StudioFragmentArgs by navArgs()
    private val viewModel: StudioViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudioBinding.inflate(layoutInflater, container, false)

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
        observeData()
        initViews()

    }
    private fun initViews(){
        viewModel.getUserInfo(args.id)
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner){ user->
            binding.user = user
        }
    }

    private fun setOnClickListeners() {
        binding.textStudioFollower.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_studioFragment_to_followerFragment)
        }
        binding.textStudioFollowing.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_studioFragment_to_followingFragment)
        }
        binding.btnStudioFollow.setOnClickListener {
            viewModel.follow(args.id)
        }
        binding.imgStudioMoreBefore.setOnClickListener {

        }
        binding.imgStudioMoreAfter.setOnClickListener {

        }
        binding.imgStudioBackAfter.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imgStudioBackBefore.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initAdapter() {
//        binding.recyclerStudioAlbum.withModels {
//            DummyDataSet.studios.forEachIndexed { index, album ->
//                studioAlbum {
//                    id(index)
//                    studio(album)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when(clickedView.id){
//                            R.id.image_ialbums_studio_thumbnail ->
//                                Navigation.findNavController(binding.root).
//                                navigate(StudioFragmentDirections.actionStudioFragmentToPictureViewerContainerFragment(model.studio().id))
//                        }
//                    }
//                }
//            }
//      }
        binding.recyclerStudioPlace.withModels {
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