package com.untilled.roadcapture.features.root.albums

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureViewerBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.pictureViewerContent
import com.untilled.roadcapture.pictureViewerThumbnail
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureViewerFragment : Fragment() {

    private var _binding: FragmentPictureViewerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerContainerViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerBinding.inflate(layoutInflater, container, false)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pictureViewerInnerContainer.setPadding(
            0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight()
        )

        onClickListeners()
    }

    private fun onClickListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        Glide.with(binding.imageviewPictureViewerBackground.context)
            .load(viewModel.album.thumbnailUrl)
            .centerCrop()
            .into(binding.imageviewPictureViewerBackground)
        PagerSnapHelper().attachToRecyclerView(binding.recyclerviewPictureViewer)
        binding.recyclerviewPictureViewer.withModels {
            pictureViewerThumbnail {
                id(1)
                album(viewModel.album)

                onClickItem { model, parentView, clickedView, position ->
                    when(clickedView.id){
                        R.id.imageview_item_picture_viewer_thumbnail_profile -> Navigation.findNavController((parentFragment as PictureViewerContainerFragment).binding.root)
                            .navigate(R.id.action_pictureViewerContainerFragment_to_studioFragment)
                    }
                }
            }
            DummyDataSet.picture.forEachIndexed { index, picture ->
                pictureViewerContent {
                    id(index)
                    picture(picture)
                }
            }

        }
    }
}