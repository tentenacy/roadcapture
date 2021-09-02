package com.untilled.roadcapture.features.root.mystudio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentMyStudioBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.studioAlbum
import com.untilled.roadcapture.studiosPlace
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_untilled_roadcapture_features_root_mystudio_MyStudioFragment_GeneratedInjector

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMyStudioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyStudioBinding.inflate(layoutInflater, container, false)
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
        binding.textviewMyStudioFollower.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followerFragment)
        }
        binding.textviewMyStudioFollowerNum.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followerFragment)
        }
        binding.textviewMyStudioFollowing.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followingFragment)
        }
        binding.textviewMyStudioFollowingNum.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_followingFragment)
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
                studiosPlace {
                    id(index)
                    place(place)
                }
            }
        }
    }

}