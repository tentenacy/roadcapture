package com.untilled.roadcapture.features.root.albums

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlbumsViewModel by viewModels()

    @Inject
    lateinit var adapter: AlbumsAdapter

    private val notificationOnClickListener: (View?) -> Unit = {
        Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
            .navigate(R.id.action_rootFragment_to_notificationFragment)
    }

    private val filterOnClickListener: (View?) -> Unit = {
        val filterBottomSheetDialog = FilterBottomSheetDialog()
        filterBottomSheetDialog.show(childFragmentManager, "filterBottomSheet")
    }

    private val albumObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        adapter.submitData(lifecycle, pagingData)
    }

    private val itemClickListener: (ItemClickArgs?) -> Unit =  { args ->
        when (args?.view?.id) {
            R.id.img_ialbums_profile -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToStudioFragment((args.item as ItemAlbumsBinding).album?.user!!.id))

            R.id.img_ialbums_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToCommentFragment((args.item as ItemAlbumsBinding).album!!.albumsId))

//            R.id.img_ialbums_like -> if (!flagLike) {
//                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
//                animator.addUpdateListener {
//                    (args.clickedView as LottieAnimationView).progress =
//                        it.animatedValue as Float
//                }
//                animator.start()
//                flagLike = true
//            } else {
//                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
//                animator.addUpdateListener {
//                    (args.clickedView as LottieAnimationView).progress =
//                        it.animatedValue as Float
//                }
//                animator.start()
//                flagLike = false
//            }
            //Todo: 네비게이션 args 변경해야 함
            R.id.img_ialbums_thumbnail,
            R.id.text_ialbums_title,
            R.id.text_ialbums_desc->
                Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
                navigate(RootFragmentDirections.actionRootFragmentToPictureViewerContainerFragment((args.item as ItemAlbumsBinding).album!!.albumsId))
            R.id.img_ialbums_more -> {
                val popupMenu = PopupMenu(requireContext(), args.view)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarAlbums)

        return binding.root
    }

    private fun addAdapter() {
        adapter.setOnClickListener(itemClickListener)
        binding.recycleAlbums.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        addAdapter()
        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner, albumObserver)
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
        refresh(null,null)
    }

    fun refresh(dateTimeFrom: String?, dateTimeTo: String?) {
        viewModel.getAlbums(AlbumsCondition(dateTimeFrom, dateTimeTo))
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