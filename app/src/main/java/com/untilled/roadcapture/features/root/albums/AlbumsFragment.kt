package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.common.AlbumMorePopupMenu
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    val binding get() = _binding!!

    private val viewModel: AlbumsViewModel by viewModels()

    private val adapter: AlbumsAdapter by lazy {
        AlbumsAdapter(itemOnClickListener)
    }

    private val notificationOnClickListener: (View?) -> Unit = {
        rootFrom3Depth().navigateToNotification()
    }

    private val filterOnClickListener: (View?) -> Unit = {
        FilterBottomSheetDialog().show(childFragmentManager, DialogTagConstant.FILTER_BOTTOM_SHEET)
    }

    private val albumObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.albums()
    }

    private val albumMenuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popupmenu_albums_more_report -> {
                showReportDialog {}
            }
            R.id.popupmenu_albums_more_hide -> {

            }
            R.id.popupmenu_albums_more_share -> {

            }
        }
        true
    }

    private fun myAlbumMenuItemClickListener(albumId: Long): (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popupmenu_myalbums_more_share -> {

            }
            R.id.popupmenu_myalbums_more_edit -> {

            }
            R.id.popupmenu_myalbums_more_del -> {
                viewModel.deleteAlbum(albumId)
            }
        }
        true
    }

    private var loadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        Logger.d("isRefreshing = ${loadState.source.refresh is LoadState.Loading}")
        binding.swipeAlbumsInnercontainer.isRefreshing =
            loadState.source.refresh is LoadState.Loading
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

        val albumUserId = (args?.item as ItemAlbumsBinding).album?.user!!.id
        val albumId = args.item.album!!.albumId
        val commentCount = args.item.album!!.commentCount
        when (args.view?.id) {
            R.id.img_ialbums_profile -> rootFrom3Depth().navigateToStudio(albumUserId)
            R.id.img_ialbums_comment -> rootFrom3Depth().navigateToComment(albumId, commentCount)
            R.id.img_ialbums_like -> {
                setLikeStatus(args.view as LottieAnimationView, args.item)
            }
            //Todo: 네비게이션 args 변경해야 함
            R.id.img_ialbums_thumbnail,
            R.id.text_ialbums_title,
            R.id.text_ialbums_desc -> rootFrom3Depth().navigateToPictureViewer(albumId)
            R.id.img_ialbums_more -> {
                if (albumUserId == User.id) MyAlbumMorePopupMenu(
                    requireContext(),
                    args.view,
                    myAlbumMenuItemClickListener(albumId)
                ).show()
                else AlbumMorePopupMenu(
                    requireContext(),
                    args.view,
                    albumMenuItemClickListener
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.albums()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)
        mainActivity().setSupportActionBar(binding.toolbarAlbums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.swipeAlbumsInnercontainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner, albumObserver)
        viewModel.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when (it.first) {
                    AlbumsViewModel.EVENT_SEARCH -> {
                        viewModel.albums()
                    }
                }
            }
        }
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
        binding.recycleAlbums.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener(loadStateListener)
    }

    private fun setLikeStatus(view: LottieAnimationView, item: ItemAlbumsBinding) {
        if (!(item.like?.liked)!!) {
            val animator = getValueAnimator(0f, 0.5f, view)
            animator.start()
            item.like!!.likeCount++
            item.like!!.liked = true
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.likeAlbum(item.album!!.albumId)
        } else {
            val animator = getValueAnimator(0.5f, 0.0f, view)
            animator.start()
            item.like!!.likeCount--
            item.like!!.liked = false
            item.textIalbumsLike.text = (item.like!!.likeCount).toString()
            viewModel.unlikeAlbum(item.album!!.albumId)
        }
    }

    private fun getValueAnimator(
        start: Float,
        end: Float,
        view: LottieAnimationView
    ): ValueAnimator {
        return ValueAnimator.ofFloat(start, end).setDuration(500).apply {
            addUpdateListener {
                view.progress = it.animatedValue as Float
            }
        }
    }
}