package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.homeAlbum
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private var flagLike: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarAlbums)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()
    }

    private fun onClickListeners() {
        binding.imageviewAlbumsSetting.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }

        binding.imageviewAlbumsNotification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(){
        binding.recyclerviewAlbums.withModels {
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