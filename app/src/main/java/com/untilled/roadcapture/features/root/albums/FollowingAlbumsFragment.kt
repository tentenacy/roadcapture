package com.untilled.roadcapture.features.root.albums

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.airbnb.epoxy.EpoxyController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentFollowingalbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.followingFilter
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingAlbumsFragment : Fragment() {

    private var _binding: FragmentFollowingalbumsBinding? = null
    private val binding get() = _binding!!
    private var flagLike: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingalbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarFollowingalbums)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageFollowingalbumsNotification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {

        binding.recyclerFollowingalbumsFilter.withModels { initFollowingAlbumsFilter() }

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

    private fun EpoxyController.initFollowingAlbumsFilter() {
        DummyDataSet.user.forEachIndexed { index, user ->
            followingFilter {
                id(index)
                user(user)

                onClickItem { model, parentView, clickedView, position ->
                    when (clickedView.id) {
                        R.id.image_ifollowing_filter_profile -> Navigation.findNavController(
                            (parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root
                        )
                            .navigate(R.id.action_rootFragment_to_studioFragment)
                    }
                }
            }
        }
    }



    private fun showReportDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dlg_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgreport_report)?.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.text_dlgreport_cancel)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}