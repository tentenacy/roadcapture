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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.AlbumsBindingModel_
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.dto.album.Albums
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.features.root.albums.dto.EpoxyItemArgs
import com.untilled.roadcapture.utils.constants.Token
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private var flagLike: Boolean = false

    private val viewModel: AlbumsViewModel by viewModels()

    private val epoxyController: AlbumsEpoxyController = AlbumsEpoxyController()

    private val epoxyItemClickListener: (EpoxyItemArgs) -> Unit =  { args ->
        when (args.clickedView.id) {
            R.id.img_ialbums_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_studioFragment)

            R.id.img_ialbums_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToCommentFragment((args.model as AlbumsBindingModel_).albums().id.toString()))

            R.id.img_ialbums_like -> if (!flagLike) {
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
                animator.addUpdateListener {
                    (args.clickedView as LottieAnimationView).progress =
                        it.animatedValue as Float
                }
                animator.start()
                flagLike = true
            } else {
                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
                animator.addUpdateListener {
                    (args.clickedView as LottieAnimationView).progress =
                        it.animatedValue as Float
                }
                animator.start()
                flagLike = false
            }
            //Todo: 네비게이션 args 변경해야 함
            R.id.img_ialbums_thumbnail,
            R.id.text_ialbums_title,
            R.id.text_ialbums_desc->
                Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
                navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment((args.model as AlbumsBindingModel_).albums().id.toString()))
            R.id.img_ialbums_more -> {
                val popupMenu = PopupMenu(requireContext(), args.clickedView)
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

    private val notificationOnClickListener: (View?) -> Unit = {
        Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
            .navigate(R.id.action_rootFragment_to_notificationFragment)
    }

    private val filterOnClickListener: (View?) -> Unit = {
        val filterBottomSheetDialog = FilterBottomSheetDialog()
        filterBottomSheetDialog.show(childFragmentManager, "filterBottomSheet")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarAlbums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageAlbumsNotification.setOnClickListener(notificationOnClickListener)
        binding.imageAlbumsFilter.setOnClickListener(filterOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun initAdapter(){
        epoxyController.setOnClickListener(epoxyItemClickListener)
        updateView(null,null)
        binding.recycleAlbums.setController(epoxyController)
    }

    fun updateView(dateTimeFrom: String?, dateTimeTo: String?) {
        lifecycleScope.launch{
            viewModel.getAlbums(token = Token.accessToken,dateTimeFrom, dateTimeTo).collectLatest{ pagingData: PagingData<Albums> ->
                epoxyController.submitData(pagingData)
            }
        }
    }


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