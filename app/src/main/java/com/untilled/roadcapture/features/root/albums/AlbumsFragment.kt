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
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.AlbumsBindingModel_
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.dto.album.Albums
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.features.base.EpoxyItemClickListener
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
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

    private val epoxyItemClickListener = object : EpoxyItemClickListener {
        override fun onClick(
            model: DataBindingEpoxyModel,
            parentView: DataBindingEpoxyModel.DataBindingHolder,
            clickedView: View,
            position: Int
        ) {
            when (clickedView.id) {
                R.id.imageview_item_home_album_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                    .navigate(R.id.action_rootFragment_to_studioFragment)

                R.id.imageview_item_home_album_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                    .navigate(RootFragmentDirections.actionRootFragmentToCommentFragment((model as AlbumsBindingModel_).albums().id.toString()))

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
                //Todo 네비게이션 args 변경해야 함
                R.id.imageview_item_home_album_thumbnail,
                R.id.textview_item_home_album_title,
                R.id.textview_item_home_album_desc->
                    Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
                    navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment((model as AlbumsBindingModel_).albums().id.toString()))
                R.id.imageview_item_home_album_more -> {
                    val popupMenu = PopupMenu(requireContext(), clickedView)
                    popupMenu.apply {
                        menuInflater.inflate(R.menu.popup_menu_albums_more, popupMenu.menu)
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
        binding.imageviewAlbumsNotification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }

        binding.imageviewAlbumsFilter.setOnClickListener {
            val filterBottomSheetDialog = FilterBottomSheetDialog()
            filterBottomSheetDialog.show(childFragmentManager, "filterBottomSheet")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    fun initAdapter(){
        epoxyController.setOnClickListener(epoxyItemClickListener)
        updateView("","")
        binding.recyclerviewAlbums.setController(epoxyController)
    }

    fun updateView(dateTimeFrom: String, dateTimeTo: String) {
        lifecycleScope.launch{
            viewModel.getAlbums(token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY0MTYzMjA3OSwiZXhwIjoxNjQxNjM1Njc5fQ.Ro6y-9YurhNMam5AP0_EwTL6MakvlbubHw2knBJAmPI",dateTimeFrom, dateTimeTo).collectLatest{ pagingData: PagingData<Albums> ->
                epoxyController.submitData(pagingData)
            }
        }
    }


    private fun showReportDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dlg_report, null)

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