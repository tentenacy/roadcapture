package com.untilled.roadcapture.features.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureViewerBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.tag.FragmentTagConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureViewerFragment : BaseFragment() {
    private var _binding: FragmentPictureViewerBinding? = null
    val binding get() = _binding!!
    private var isMapScreen = false

    private lateinit var pictureSliderFragment: PictureSliderFragment
    private lateinit var pictureMapFragment: PictureMapFragment

    private val viewModel: PictureViewerViewModel by viewModels()
    private val args: PictureViewerFragmentArgs by navArgs()

    private val switchOnClickListener: (View?) -> Unit = {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setChildFragmentTransaction()
        viewModel.albumDetail(args.id)
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

        binding.constraintPictureViewerContainer.setStatusBarTransparent(mainActivity())
        setIconWhite()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mainActivity().setStatusBarOrigin()

        _binding = null
    }

    private fun setOnClickListeners() {
        binding.imagePictureViewerBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
        binding.fabPictureViewerSwitch.setOnClickListener(switchOnClickListener)
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

    private fun setChildFragmentTransaction() {
        pictureSliderFragment = PictureSliderFragment()
        pictureMapFragment = PictureMapFragment()

        childFragmentManager.beginTransaction().apply {
            add(
                R.id.frame_picture_viewer_container,
                pictureSliderFragment,
                FragmentTagConstant.PICTURE_VIEWER_SLIDER
            )
            add(
                R.id.frame_picture_viewer_container,
                pictureMapFragment,
                FragmentTagConstant.PICTURE_VIEWER_MAP
            )
            hide(pictureMapFragment)
        }.commit()
    }
}