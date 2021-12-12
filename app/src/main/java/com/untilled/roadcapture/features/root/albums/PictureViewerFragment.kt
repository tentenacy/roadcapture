package com.untilled.roadcapture.features.root.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.bumptech.glide.Glide
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.FragmentPictureViewerBinding
import com.untilled.roadcapture.pictureViewerContent
import com.untilled.roadcapture.pictureViewerThumbnail
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureViewerFragment : Fragment() {

    private var _binding: FragmentPictureViewerBinding? = null
    private val binding get() = _binding!!
    private var album: Album? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarPictureViewer)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        val args: PictureViewerFragmentArgs by navArgs()
        Glide.with(binding.imageviewPictureViewerBackground.context)
            .load(args.album.thumbnailUrl)
            .centerCrop()
            .into(binding.imageviewPictureViewerBackground)
        PagerSnapHelper().attachToRecyclerView(binding.recyclerviewPictureViewer)
        binding.recyclerviewPictureViewer.withModels {
            pictureViewerThumbnail {
                id(1)
                album(args.album)
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