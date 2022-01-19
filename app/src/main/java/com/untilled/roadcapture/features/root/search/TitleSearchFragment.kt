package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentTitleSearchBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.titleSearch
import com.untilled.roadcapture.utils.dummy.DummyDataSet

class TitleSearchFragment : Fragment() {

    private var _binding: FragmentTitleSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTitleSearchBinding.inflate(layoutInflater, container, false)

        initAdapter()

        Logger.d("화면 전환 시 호출되나?")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        binding.recyclerTitlesearch.withModels {
//            DummyDataSet.albums.forEachIndexed { index, album ->
//                titleSearch {
//                    id(index)
//                    album(album)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when (clickedView.id) {
//                            R.id.img_ititlesearch_profile -> Navigation.findNavController(
//                                (parentFragment?.parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root
//                            ).navigate(R.id.action_rootFragment_to_studioFragment)
//
//                            R.id.imageview_item_title_search_thumbnail -> Navigation.findNavController(
//                                (parentFragment?.parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root
//                            ).navigate(
//                                RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment(
//                                    model.album()
//                                )
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}