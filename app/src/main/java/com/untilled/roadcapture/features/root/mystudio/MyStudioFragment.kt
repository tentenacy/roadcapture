package com.untilled.roadcapture.features.root.mystudio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.carousel
import com.google.android.material.imageview.ShapeableImageView
import com.untilled.roadcapture.*
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentMyStudioBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.features.root.search.SearchFragment
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMyStudioBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyStudioBinding.inflate(layoutInflater, container, false)
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
        binding.textviewMyStudioFollowerNum.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followerFragment)
        }
        binding.textviewMyStudioFollowingNum.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followingFragment)
        }
        binding.buttonMyStudioEditProfile.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user))
        }
        binding.imageviewMyStudioSetting.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
        binding.imageviewMyStudioTitleSetting.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
    }

    private fun initAdapter() {
        binding.recyclerviewMyStudioAlbums.withModels {
            DummyDataSet.studios.forEachIndexed { index, album ->
                studioAlbum {
                    id(index)
                    studio(album)
                }
            }

        }
        binding.recyclerviewMyStudioPlace.withModels {
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