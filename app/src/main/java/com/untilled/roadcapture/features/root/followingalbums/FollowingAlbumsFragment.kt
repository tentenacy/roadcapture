package com.untilled.roadcapture.features.root.followingalbums

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.databinding.FragmentFollowingalbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemFollowingFilterBinding
import com.untilled.roadcapture.features.common.ReportDialogFragment
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingAlbumsFragment : Fragment() {

    private var _binding: FragmentFollowingalbumsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowingAlbumsViewModel by viewModels()

    private var followingId: Long? = null

    private val followingAlbumsAdapter: FollowingAlbumsAdapter by lazy {
        FollowingAlbumsAdapter(albumItemOnClickListener)
    }
    private val followingAlbumsFilterAdapter: FollowingAlbumsFilterAdapter by lazy{
        FollowingAlbumsFilterAdapter(filterItemOnClickListener)
    }

    private val followingAlbumsObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        followingAlbumsAdapter.submitData(lifecycle, pagingData)
    }

    private val followingAlbumsFilterObserver: (PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>) -> Unit = { pagingData ->
        followingAlbumsFilterAdapter.submitData(lifecycle, pagingData)
    }

    private val notificationOnClickListener: (View?) -> Unit = {
        rootFrom3Depth().navigateToNotification()
    }

    private val filterItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        val position = (args?.item as ItemFollowingFilterBinding).position
        followingId = (args.item).user?.followingSortByAlbumId
        getSelectedAlbums(position, followingId)
    }

    private val albumItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

        val albumUserId = (args?.item as ItemAlbumsBinding).album?.user!!.id
        val albumId = args.item.album!!.albumId

        when (args.view?.id) {
            R.id.img_ialbums_profile -> rootFrom3Depth().navigateToStudio(albumUserId)
            R.id.img_ialbums_comment -> rootFrom3Depth().navigateToComment(albumId)
            R.id.img_ialbums_like -> {
                setLikeStatus(args.view as LottieAnimationView, args.item)
            }
            //Todo: 네비게이션 args 변경해야 함
            R.id.img_ialbums_thumbnail,
            R.id.text_ialbums_title,
            R.id.text_ialbums_desc -> rootFrom3Depth().navigateToPictureViewer(albumId)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refresh(followingId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingalbumsBinding.inflate(layoutInflater, container, false)

        mainActivity().setSupportActionBar(binding.toolbarFollowingalbums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.followingAlbums.observe(viewLifecycleOwner, followingAlbumsObserver)
        viewModel.followingsSortByAlbum.observe(viewLifecycleOwner, followingAlbumsFilterObserver)
    }

    fun initAdapter() {
        binding.recyclerFollowingalbums.adapter = followingAlbumsAdapter
        binding.recyclerFollowingalbumsFilter.adapter = followingAlbumsFilterAdapter
    }

    private fun refresh(followingId: Long?) {
        viewModel.getFollowingAlbums(FollowingAlbumsCondition(followingId))
        viewModel.getFollowingsSortByAlbum()
    }

    private fun setOnClickListeners() {
        binding.imageFollowingalbumsNotification.setOnClickListener(notificationOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun showReportDialog() {
        ReportDialogFragment({}).show(childFragmentManager, DialogTagConstant.REPORT_DIALOG)
    }

    private fun setLikeStatus(view: LottieAnimationView, item: ItemAlbumsBinding) {
        if (!(item.like?.liked)!!) {
            val animator = getValueAnimator(0f,0.5f, view)
            animator.start()
            item.like!!.likeCount++
            item.like!!.liked = true
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.likeAlbum(item.album!!.albumId)
        } else {
            val animator = getValueAnimator(0.5f,0.0f, view)
            animator.start()
            item.like!!.likeCount--
            item.like!!.liked = false
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.unlikeAlbum(item.album!!.albumId)
        }
    }

    private fun getValueAnimator(start: Float, end: Float, view: LottieAnimationView): ValueAnimator {
        val animator = ValueAnimator.ofFloat(start, end).setDuration(500)
        animator.addUpdateListener {
            view.progress = it.animatedValue as Float
        }
        return animator
    }

    private fun getSelectedAlbums(position: Int?, followingId: Long?) {
        if (followingAlbumsFilterAdapter.index == position) {
            followingAlbumsFilterAdapter.index = null
            refresh(null)
        } else {
            followingAlbumsFilterAdapter.index = position
            refresh(followingId)
        }
        followingAlbumsFilterAdapter.notifyDataSetChanged()
    }
}