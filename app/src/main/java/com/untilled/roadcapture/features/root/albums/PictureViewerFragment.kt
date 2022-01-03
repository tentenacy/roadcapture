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
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.dto.album.AlbumResponse
import com.untilled.roadcapture.databinding.FragmentPictureViewerBinding
import com.untilled.roadcapture.pictureViewerContent
import com.untilled.roadcapture.pictureViewerThumbnail
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PictureViewerFragment : Fragment() {

    private var _binding: FragmentPictureViewerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerContainerViewModel by viewModels({requireParentFragment()})

    private var flagLike: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureViewerBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pictureViewerInnerContainer.setPadding(
            0, requireContext().statusBarHeight(), 0, requireContext().navigationHeight()
        )
        subscribeUi()
        setOnClickListeners()
    }

    private fun subscribeUi(){
        viewModel.albumResponse.observe(viewLifecycleOwner){
            initAdapter(it)
        }
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureViewerComment.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(PictureViewerContainerFragmentDirections.actionPictureViewerContainerFragmentToCommentFragment(viewModel.albumResponse.value?.id.toString()))
        }
        binding.imageviewPictureViewerLike.setOnClickListener { lottie ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(albumResponse: AlbumResponse) {
        Glide.with(binding.imageviewPictureViewerBackground.context)
            .load(albumResponse.thumbnailUrl)
            .centerCrop()
            .into(binding.imageviewPictureViewerBackground)
        if(binding.recyclerviewPictureViewer.onFlingListener == null) {
            PagerSnapHelper().attachToRecyclerView(binding.recyclerviewPictureViewer)
        }
        binding.recyclerviewPictureViewer.withModels {
            pictureViewerThumbnail {
                id(1)
                album(albumResponse)
                onClickItem { model, parentView, clickedView, position ->
                    when(clickedView.id){
                        R.id.imageview_item_picture_viewer_thumbnail_profile -> Navigation.findNavController((parentFragment as PictureViewerContainerFragment).binding.root)
                            .navigate(R.id.action_pictureViewerContainerFragment_to_studioFragment)
                    }
                }
            }
            albumResponse.pictureResponses.forEachIndexed { index, picture ->
                pictureViewerContent {
                    id(index)
                    picture(picture)
                }
            }

        }
    }
}