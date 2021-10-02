package com.untilled.roadcapture.features.root.mystudio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentMyStudioBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.search.SearchFragment
import com.untilled.roadcapture.studioAlbum
import com.untilled.roadcapture.studiosPlace
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_untilled_roadcapture_features_root_mystudio_MyStudioFragment_GeneratedInjector

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
        binding.imageviewMyStudioModification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_myStudioModification)
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

                    onClickItem { model, parentView, clickedView, position ->
                        val root = parentFragment?.parentFragment?.parentFragment as RootFragment
                        val searchStackHostFragment =
                            root.childFragmentManager.findFragmentByTag("searchRootFragment") as StackHostFragment
                        val myStudioStackHostFragment =
                            root.childFragmentManager.findFragmentByTag("myStudioFragment") as StackHostFragment

                        val searchFragment =
                            searchStackHostFragment.childFragmentManager.fragments.first().childFragmentManager.fragments.first() as SearchFragment
                        searchFragment.binding.edittextSearchInput.setText((clickedView as Button).text.toString())
                        searchFragment.setSearchFragmentTab()

                        root.setupTabSelectedState(1)

                        root.childFragmentManager.beginTransaction()
                            .show(searchStackHostFragment)
                            .hide(myStudioStackHostFragment)
                            .commit()
                    }
                }
            }
        }
    }

}