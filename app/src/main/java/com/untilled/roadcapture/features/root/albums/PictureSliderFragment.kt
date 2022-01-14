package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import com.airbnb.epoxy.EpoxyController
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.api.dto.album.AlbumResponse
import com.untilled.roadcapture.databinding.FragmentPictureSliderBinding
import com.untilled.roadcapture.features.root.comment.CommentBottomSheetDialog
import com.untilled.roadcapture.pictureSliderContent
import com.untilled.roadcapture.pictureSliderThumbnail
import com.untilled.roadcapture.utils.navigationHeight
import com.untilled.roadcapture.utils.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureSliderFragment : Fragment() {

    private var _binding: FragmentPictureSliderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerViewModel by viewModels({ requireParentFragment() })

    private val commentOnClickListener: (View?) -> Unit = {
        CommentBottomSheetDialog().show(childFragmentManager, "commentBottomSheet")
    }
    private val likeOnClickListener: (View?) -> Unit = { lottie ->
        if (!flagLike) {
            val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
            animator.addUpdateListener {
                (lottie as LottieAnimationView).progress =
                    it.animatedValue as Float
            }
            animator.start()
            flagLike = true
        } else {
            val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
            animator.addUpdateListener {
                (lottie as LottieAnimationView).progress =
                    it.animatedValue as Float
            }
            animator.start()
            flagLike = false
        }
    }
    private var flagLike: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureSliderBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarTransparent()
        subscribeUi()
        setOnClickListeners()
    }

    private fun setStatusBarTransparent() {
        binding.pictureViewerContainer.setPadding(
            0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight()
        )
    }

    private fun subscribeUi() {
        viewModel.albumResponse.observe(viewLifecycleOwner) { albumResponse ->
            setThumbnailToBackground(albumResponse)
            initAdapter(albumResponse)
        }
    }


    private fun setOnClickListeners() {
        binding.imagePictureSliderComment.setOnClickListener(commentOnClickListener)
        binding.imagePictureSliderLike.setOnClickListener(likeOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(albumResponse: AlbumResponse) {
        addScrollListener()
        binding.recyclerPictureSlider.withModels {
            initPictureSliderThumbnail(albumResponse)
            initPictureSliderContent(albumResponse)
        }
    }

    private fun EpoxyController.initPictureSliderContent(albumResponse: AlbumResponse) {
        albumResponse.pictureResponses.forEachIndexed { index, picture ->
            pictureSliderContent {
                id(index)
                picture(picture)
            }
        }
    }

    private fun EpoxyController.initPictureSliderThumbnail(albumResponse: AlbumResponse) {
        pictureSliderThumbnail {
            id(1)
            album(albumResponse)
            onClickItem { model, parentView, clickedView, position ->
                when (clickedView.id) {
                    R.id.img_ipicture_slider_thumbnail_profile -> Navigation.findNavController(
                        (parentFragment as PictureViewerFragment).binding.root
                    )
                        .navigate(R.id.action_pictureViewerContainerFragment_to_studioFragment)
                }
            }
        }
    }

    private fun setThumbnailToBackground(albumResponse: AlbumResponse) {
        Glide.with(binding.imagePictureSliderBg.context)
            .load(albumResponse.thumbnailUrl)
            .centerCrop()
            .into(binding.imagePictureSliderBg)
    }

    private fun addScrollListener() {
        if (binding.recyclerPictureSlider.onFlingListener == null) {
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerPictureSlider)
            val pictureSnapPagerScrollListener: PictureSnapPagerScrollListener =
                PictureSnapPagerScrollListener(
                    pagerSnapHelper,
                    PictureSnapPagerScrollListener.ON_SETTLED,
                    true,
                    object : PictureSnapPagerScrollListener.OnChangeListener {
                        override fun onSnapped(position: Int) {
                            viewModel.currentPosition = position
                        }
                    }
                )
            binding.recyclerPictureSlider.addOnScrollListener(pictureSnapPagerScrollListener)
        }
    }
}