package com.untilled.roadcapture.features.root.followingalbums

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.datasource.api.dto.common.PageRequest
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.FollowingsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.datasource.sharedpref.Token
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.databinding.FragmentFollowingalbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingAlbumsFragment : Fragment() {

    private var _binding: FragmentFollowingalbumsBinding? = null
    private val binding get() = _binding!!

    private val userObserver = { user: PageResponse<UsersResponse> ->
        initFilterAdapter(user)
    }
/*
    private val albumsObserver = { albums: PageResponse<AlbumsResponse> ->
        initAlbumAdapter(albums)
    }
*/

    private val notificationOnClickListener: (View?) -> Unit = {
        Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
            .navigate(R.id.action_rootFragment_to_notificationFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingalbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarFollowingalbums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Test", Token.accessToken)
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun initViews(){
    }

    private fun observeData() {
    }

    private fun setOnClickListeners() {
        binding.imageFollowingalbumsNotification.setOnClickListener(notificationOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initFilterAdapter(user: PageResponse<UsersResponse>) {
    }
/*
    private fun initAlbumAdapter(albums: PageResponse<AlbumsResponse>){
        binding.recyclerFollowingalbums.withModels { initFollowingAlbumsItem(albums) }
    }
*/

/*
    private fun EpoxyController.initFollowingAlbumsItem(albums: PageResponse<AlbumsResponse>) {
        albums.content.forEachIndexed { index, albumResponse ->
            albums {
                id(index)
                albums(albumResponse)

                onClickItem { model, parentView, clickedView, position ->
                    when (clickedView.id) {
                        R.id.img_ialbums_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                            .navigate(R.id.action_rootFragment_to_studioFragment)

                        R.id.img_ialbums_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                            .navigate(RootFragmentDirections.actionRootFragmentToCommentFragment((model as AlbumsBindingModel_).albums().id.toString()))

                        R.id.img_ialbums_like -> if (!flagLike) {
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
                        //Todo: 네비게이션 args 변경해야 함
                        R.id.img_ialbums_thumbnail,
                        R.id.text_ialbums_title,
                        R.id.text_ialbums_desc ->
                            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                                .navigate(
                                    RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment(
                                        (model as AlbumsBindingModel_).albums().id.toInt())
                                    )

                        R.id.img_ialbums_more -> {
                            val popupMenu = PopupMenu(requireContext(), clickedView)
                            popupMenu.apply {
                                menuInflater.inflate(R.menu.popupmenu_albums_more, popupMenu.menu)
                                setOnMenuItemClickListener { item ->
                                    when (item.itemId) {
                                        R.id.popup_menu_albums_more_share -> {
                                        }
                                        R.id.popup_menu_albums_more_report -> {
                                            showReportDialog()
                                        }
                                        R.id.popup_menu_albums_more_hide -> {
                                        }
                                    }
                                    true
                                }
                            }.show()
                        }
                    }
                }
            }
        }
    }
*/

    private fun showReportDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dlg_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgreport_confirm)?.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.dlgreport_cancel)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}