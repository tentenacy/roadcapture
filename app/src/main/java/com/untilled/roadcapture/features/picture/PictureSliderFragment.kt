package com.untilled.roadcapture.features.picture

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.databinding.FragmentPictureSliderBinding
import com.untilled.roadcapture.features.picture.listener.PictureSnapPagerScrollListener
import com.untilled.roadcapture.utils.setPaddingWhenStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureSliderFragment : Fragment() {

    private var _binding: FragmentPictureSliderBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerViewModel by viewModels({ requireParentFragment() })
    private val adapter: PictureViewerAdapter by lazy {
        PictureViewerAdapter()
    }

    private val albumObserver: (AlbumResponse) -> Unit = { albumResponse ->
        adapter.run {
            item = albumResponse
            notifyDataSetChanged()
        }
    }

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
        observeData()
        addScrollListener()
        initAdapter()
        setOnClickListeners()
    }

    private fun initAdapter() {
        binding.recyclerPictureSlider.adapter = adapter
    }

    private fun setStatusBarTransparent() {
        binding.pictureViewerContainer.setPaddingWhenStatusBarTransparent(requireContext())
    }

    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner,albumObserver)
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
    }

    private fun setThumbnailToBackground(albumResponse: AlbumResponse) {
        //TODO null 대신 isThumbnail 있는 picture로
//        Glide.with(binding.imagePictureSliderBg.context)
//            .load(null)
//            .centerCrop()
//            .into(binding.imagePictureSliderBg)
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