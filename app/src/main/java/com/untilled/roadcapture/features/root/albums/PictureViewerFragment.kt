package com.untilled.roadcapture.features.root.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureViewerBinding
import com.untilled.roadcapture.utils.constants.Token
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.setStatusBarOrigin
import com.untilled.roadcapture.utils.extension.setStatusBarTransparent
import com.untilled.roadcapture.utils.extension.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureViewerFragment : Fragment() {
    private var _binding: FragmentPictureViewerBinding? = null
    val binding get() = _binding!!
    private var isMapScreen = false

    private lateinit var pictureSliderFragment: PictureSliderFragment
    private lateinit var pictureMapFragment: PictureMapFragment

    private val viewModel: PictureViewerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pictureSliderFragment = PictureSliderFragment()
        pictureMapFragment = PictureMapFragment()

        childFragmentManager.beginTransaction().apply {
            add(
                R.id.frame_picture_viewer_container,
                pictureMapFragment,
                "PictureViewerMapFragment"
            )
            hide(pictureMapFragment)
            add(
                R.id.frame_picture_viewer_container,
                pictureSliderFragment,
                "PictureViewerFragment"
            )
        }.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PictureViewerFragmentArgs by navArgs()
        viewModel.getAlbumDetail(token = Token.accessToken,args.id)
//        viewModel.id = args.id
        requireActivity().setStatusBarTransparent()
        binding.constraintPictureViewerContainer.setPadding(
            0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight()
        )

        setIconWhite()

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imagePictureViewerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabPictureViewerSwitch.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                isMapScreen = if(isMapScreen) {
                    show(pictureSliderFragment)
                    hide(pictureMapFragment)
                    setIconWhite()
                    false
                } else {
                    show(pictureMapFragment)
                    hide(pictureSliderFragment)
                    setIconBlack()
                    true
                }
            }.commit()
        }
    }

    private fun setIconWhite() {
        binding.run {
            imagePictureViewerBack.setColorFilter(requireContext().getColor(R.color.white))
            imagePictureViewerShare.setColorFilter(requireContext().getColor(R.color.white))
            fabPictureViewerSwitch.run {
                setImageResource(R.drawable.ic_map)
                setColorFilter(requireContext().getColor(R.color.secondaryColor))
                backgroundTintList = AppCompatResources.getColorStateList(requireContext(), android.R.color.white)
            }
        }
    }

    private fun setIconBlack() {
        binding.run {
            imagePictureViewerBack.setColorFilter(requireContext().getColor(R.color.black))
            imagePictureViewerShare.setColorFilter(requireContext().getColor(R.color.black))
            fabPictureViewerSwitch.run {
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