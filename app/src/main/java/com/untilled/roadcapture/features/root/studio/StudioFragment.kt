package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.*
import com.untilled.roadcapture.databinding.FragmentStudioBinding
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioFragment : Fragment() {

    private var _binding: FragmentStudioBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudioBinding.inflate(layoutInflater, container, false)
        binding.user = DummyDataSet.studioUser
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

    private fun setOnClickListeners() {
        binding.textviewStudioFollowerNum.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_studioFragment_to_followerFragment)
        }
        binding.textviewStudioFollowingNum.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_studioFragment_to_followingFragment)
        }
        binding.buttonStudioEditProfile.setOnClickListener {

        }
        binding.imageviewStudioMorePrevious.setOnClickListener {

        }
        binding.imageviewStudioMoreAfter.setOnClickListener {

        }
        binding.imageviewStudioBackAfter.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imageviewStudioBackBefore.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initAdapter() {
        binding.recyclerviewStudioAlbum.withModels {
            DummyDataSet.studios.forEachIndexed { index, album ->
                studioAlbum {
                    id(index)
                    studio(album)
                }
            }

        }
        binding.recyclerviewStudioPlace.withModels {
            DummyDataSet.places.forEachIndexed { index, place ->
                studioPlace {
                    id(index)
                    place(place)

                    onClickItem { model, parentView, clickedView, position ->
                        when(clickedView.id){
                            R.id.view_item_studio_place_overlay ->
                                clickedView.isSelected = !clickedView.isSelected
                        }
                    }
                }
            }
        }
    }
}