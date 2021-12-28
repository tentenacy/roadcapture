package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentFollowingAlbumBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.followingAlbumNews
import com.untilled.roadcapture.homeAlbum
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingAlbumFragment : Fragment() {

    private var _binding: FragmentFollowingAlbumBinding? = null
    private val binding get() = _binding!!
    private var flagLike: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingAlbumBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarFollowingAlbum)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewFollowingAlbumNotification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(){

        binding.recyclerviewFollowingAlbumNews.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                followingAlbumNews {
                    id(index)
                    user(user)

                    onClickItem { model, parentView, clickedView, position ->
                        when (clickedView.id) {
                            R.id.imageview_item_following_album_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                                .navigate(R.id.action_rootFragment_to_studioFragment)
                        }
                    }
                }
            }
        }

//        binding.recyclerviewFollowingAlbum.withModels {
//            DummyDataSet.albums.forEachIndexed { index, album ->
//                homeAlbum {
//                    id(index)
//                    album(album)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when (clickedView.id) {
//                            R.id.imageview_item_home_album_thumbnail,
//                            R.id.textview_item_home_album_title,
//                            R.id.textview_item_home_album_desc
//                            -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
//                                navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment(model.album()))
//
//                            R.id.imageview_item_home_album_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
//                                .navigate(R.id.action_rootFragment_to_studioFragment)
//
//                            R.id.imageview_item_home_album_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
//                                .navigate(R.id.action_rootFragment_to_commentFragment)
//
//                            R.id.imageview_item_home_album_like -> if (!flagLike) {
//                                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
//                                animator.addUpdateListener {
//                                    (clickedView as LottieAnimationView).progress =
//                                        it.animatedValue as Float
//                                }
//                                animator.start()
//                                flagLike = true
//                            } else {
//                                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
//                                animator.addUpdateListener {
//                                    (clickedView as LottieAnimationView).progress =
//                                        it.animatedValue as Float
//                                }
//                                animator.start()
//                                flagLike = false
//                            }
//                            R.id.imageview_item_home_album_more -> {
//                                val popupMenu = PopupMenu(requireContext(), clickedView)
//                                popupMenu.apply {
//                                    menuInflater.inflate(R.menu.popup_menu_albums_more, popupMenu.menu)
//                                    setOnMenuItemClickListener { item ->
//                                        when (item.itemId) {
//                                            R.id.popup_menu_albums_more_share -> {
//                                            }
//                                            R.id.popup_menu_albums_more_report -> {
//                                                showReportDialog()
//                                            }
//                                            R.id.popup_menu_albums_more_hide -> {
//                                            }
//                                        }
//                                        true
//                                    }
//                                }.show()
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun showReportDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewReport = dialogView.findViewById<TextView>(R.id.textview_report_report)
        val textViewCancel = dialogView.findViewById<TextView>(R.id.textview_report_cancel)

        textViewReport?.setOnClickListener {
            dialog.dismiss()
        }
        textViewCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}