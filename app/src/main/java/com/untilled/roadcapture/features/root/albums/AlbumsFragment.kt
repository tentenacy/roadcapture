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
import androidx.paging.PagingData
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
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
        rootFromChild().navigateToNotification()
    }

    private val filterOnClickListener: (View?) -> Unit = {
        val filterBottomSheetDialog = FilterBottomSheetDialog()
        filterBottomSheetDialog.show(childFragmentManager, "filterBottomSheet")
    }

    private val albumObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        adapter.submitData(lifecycle, pagingData)
    }

    private val itemClickListener: (ItemClickArgs?) -> Unit = { args ->

        val albumUserId = (args?.item as ItemAlbumsBinding).album?.user!!.id
        val albumId = args.item.album!!.albumsId

        when (args.view?.id) {
            R.id.img_ialbums_profile -> rootFromChild().navigateToStudio(albumUserId)
            R.id.img_ialbums_comment -> rootFromChild().navigateToComment(albumId)
            R.id.img_ialbums_like -> {
                setLikeStatus(args.view as LottieAnimationView, args.item)
            }
            //Todo: 네비게이션 args 변경해야 함
            R.id.img_ialbums_thumbnail,
            R.id.text_ialbums_title,
            R.id.text_ialbums_desc -> rootFromChild().navigateToPictureViewerContainer(albumId)
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

    private fun setLikeStatus(view: LottieAnimationView, item: ItemAlbumsBinding) {
        if (!(item.like?.liked)!!) {
            val animator = getValueAnimator(0f,0.5f, view)
            animator.start()
            item.like!!.likeCount++
            item.like!!.liked = true
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.likesAlbum(item.album!!.albumsId)
        } else {
            val animator = getValueAnimator(0.5f,0.0f, view)
            animator.start()
            item.like!!.likeCount--
            item.like!!.liked = false
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.unlikesAlbum(item.album!!.albumsId)
        }
    }

    private fun getValueAnimator(start: Float, end: Float, view: LottieAnimationView): ValueAnimator {
        val animator = ValueAnimator.ofFloat(start, end).setDuration(500)
        animator.addUpdateListener {
            view.progress = it.animatedValue as Float
        }
        return animator
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

    fun initAdapter() {
        adapter.itemClickListener = itemClickListener
        binding.recycleAlbums.adapter = adapter
        refresh(null, null)
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