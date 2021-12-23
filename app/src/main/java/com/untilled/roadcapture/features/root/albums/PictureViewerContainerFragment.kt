package com.untilled.roadcapture.features.root.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureViewerContainerBinding

class PictureViewerContainerFragment : Fragment() {
    private var _binding: FragmentPictureViewerContainerBinding? = null
    private val binding get() = _binding!!

    private var isMapScreen = false

    private lateinit var pictureViewerFragment: PictureViewerFragment
    private lateinit var pictureViewerMapFragment: PictureViewerMapFragment

    private val viewModel: PictureViewerContainerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pictureViewerFragment = PictureViewerFragment()
        pictureViewerMapFragment = PictureViewerMapFragment()

        childFragmentManager.beginTransaction().apply {
            add(
                R.id.framelayout_picture_viewer_container,
                pictureViewerMapFragment,
                "PictureViewerMapFragment"
            )
            add(
                R.id.framelayout_picture_viewer_container,
                pictureViewerFragment,
                "PictureViewerFragment"
            )
        }.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PictureViewerContainerFragmentArgs by navArgs()
        viewModel.album = args.albums

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureViewerContainerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabMap.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                isMapScreen = if(isMapScreen) {
                    show(pictureViewerFragment)
                    hide(pictureViewerMapFragment)
                    false
                } else {
                    show(pictureViewerMapFragment)
                    hide(pictureViewerFragment)
                    true
                }
            }.commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}