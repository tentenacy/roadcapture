package com.untilled.roadcapture.features.root.search

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentTitleSearchBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.homeAlbum
import com.untilled.roadcapture.notification
import com.untilled.roadcapture.utils.DummyDataSet

class TitleSearchFragment : Fragment() {

    private var _binding: FragmentTitleSearchBinding? = null
    private val binding get() = _binding!!
    private var flagLike = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTitleSearchBinding.inflate(layoutInflater, container, false)

        initAdapter()

        Logger.d("화면 전환 시 호출되나?")

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(){
        binding.recyclerviewTitleSearch.withModels {
            DummyDataSet.albums.forEachIndexed { index, album ->
                homeAlbum {
                    id(index)
                    album(album)

                    onClickItem { model, parentView, clickedView, position ->
                        when (clickedView.id) {
                            R.id.imageview_item_home_album_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                                .navigate(R.id.action_rootFragment_to_commentFragment)

                            R.id.imageview_item_home_album_like -> if (!flagLike) {
                                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
                                animator.addUpdateListener {
                                    (clickedView as LottieAnimationView).progress =
                                        it.animatedValue as Float
                                }
                                animator.start()
                                flagLike = true
                            } else {
                                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
                                animator.addUpdateListener {
                                    (clickedView as LottieAnimationView).progress =
                                        it.animatedValue as Float
                                }
                                animator.start()
                                flagLike = false
                            }

                        }
                    }
                }
            }
        }
    }
}