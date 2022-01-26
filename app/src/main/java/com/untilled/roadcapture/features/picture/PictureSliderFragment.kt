package com.untilled.roadcapture.features.picture

import android.animation.ValueAnimator
import android.annotation.SuppressLint
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
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.setPaddingWhenStatusBarTransparent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureSliderFragment: Fragment() {

    private var _binding: FragmentPictureSliderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureViewerViewModel by viewModels({ requireParentFragment() })

    private val adapter: PictureViewerAdapter by lazy {
        PictureViewerAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private val albumObserver: (AlbumResponse) -> Unit = { albumResponse ->
        binding.liked = albumResponse.liked
        adapter.apply {
            item = albumResponse
            notifyDataSetChanged()
        }
    }

    private val commentOnClickListener: (View?) -> Unit = {
        CommentBottomSheetDialog().show(childFragmentManager, DialogTagConstant.COMMENT_BOTTOM_SHEET)
    }

    private val likeOnClickListener: (View?) -> Unit = {
        viewModel.liked.observe(viewLifecycleOwner, likedObserver)
        viewModel.likeOrUnlike()
    }

    private val likedObserver: (Boolean) -> Unit = { liked ->
        if (liked) {
            getValueAnimator(0f, 0.5f, binding.imagePictureSliderLike).start()
        } else {
            getValueAnimator(0.5f, 0.0f, binding.imagePictureSliderLike).start()
        }
    }

    private val currentPositionObserver: (Int) -> Unit = { position ->
        viewModel.changeCommentCount()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureSliderBinding.inflate(layoutInflater, container, false)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStatusBarTransparent()
        setScrollListener()
        initAdapter()
        observeData()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        binding.recyclerPictureSlider.adapter = adapter
    }

    private fun setStatusBarTransparent() {
        binding.pictureViewerContainer.setPaddingWhenStatusBarTransparent(requireContext())
    }

    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner, albumObserver)
        viewModel.currentPosition.observe(viewLifecycleOwner, currentPositionObserver)
    }

    private fun getValueAnimator(start: Float, end: Float, view: LottieAnimationView): ValueAnimator {
        return ValueAnimator.ofFloat(start, end).setDuration(500).apply {
            addUpdateListener {
                view.progress = it.animatedValue as Float
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imagePictureSliderComment.setOnClickListener(commentOnClickListener)
        binding.imagePictureSliderLike.setOnClickListener(likeOnClickListener)
    }

    private fun setScrollListener() {
        if (binding.recyclerPictureSlider.onFlingListener == null) {
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerPictureSlider)
            val pictureSnapPagerScrollListener =
                PictureSnapPagerScrollListener(
                    pagerSnapHelper,
                    PictureSnapPagerScrollListener.ON_SETTLED,
                    true,
                    object : PictureSnapPagerScrollListener.OnChangeListener {
                        override fun onSnapped(position: Int) {
                            viewModel.setCurrentPosition(position)
                        }
                    }
                )
            binding.recyclerPictureSlider.addOnScrollListener(pictureSnapPagerScrollListener)
        }
    }
}