package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.repository.albums.AlbumsRepository
import com.untilled.roadcapture.databinding.FragmentPictureViewerContainerBinding
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.setStatusBarOrigin
import com.untilled.roadcapture.utils.extension.setStatusBarTransparent
import com.untilled.roadcapture.utils.extension.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureViewerContainerFragment : Fragment() {
    private var _binding: FragmentPictureViewerContainerBinding? = null
    val binding get() = _binding!!
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
            hide(pictureViewerMapFragment)
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
//        viewModel.getAlbumDetail(args.id)
        viewModel.id = args.id
        requireActivity().setStatusBarTransparent()
        binding.pictureViewerContainerInnerContainer.setPadding(
            0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight()
        )

        setIconWhite()

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureViewerContainerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabPictureViewerContainerSwitch.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                isMapScreen = if(isMapScreen) {
                    show(pictureViewerFragment)
                    hide(pictureViewerMapFragment)
                    setIconWhite()
                    false
                } else {
                    show(pictureViewerMapFragment)
                    hide(pictureViewerFragment)
                    setIconBlack()
                    true
                }
            }.commit()
        }
    }

    private fun setIconWhite() {
        binding.run {
            imageviewPictureViewerContainerBack.setColorFilter(requireContext().getColor(R.color.white))
            imageviewPictureViewerContainerShare.setColorFilter(requireContext().getColor(R.color.white))
            fabPictureViewerContainerSwitch.run {
                setImageResource(R.drawable.ic_map)
                setColorFilter(requireContext().getColor(R.color.secondaryColor))
                backgroundTintList = AppCompatResources.getColorStateList(requireContext(), android.R.color.white)
            }
        }
    }

    private fun setIconBlack() {
        binding.run {
            imageviewPictureViewerContainerBack.setColorFilter(requireContext().getColor(R.color.black))
            imageviewPictureViewerContainerShare.setColorFilter(requireContext().getColor(R.color.black))
            fabPictureViewerContainerSwitch.run {
                setImageResource(R.drawable.ic_album)
                setColorFilter(requireContext().getColor(R.color.white))
                backgroundTintList = AppCompatResources.getColorStateList(requireContext(), R.color.secondaryColor)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().setStatusBarOrigin()

        _binding = null
    }
}