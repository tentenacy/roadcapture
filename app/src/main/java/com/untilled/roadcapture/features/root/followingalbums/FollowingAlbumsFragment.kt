package com.untilled.roadcapture.features.root.followingalbums

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.insertHeaderItem
import androidx.paging.map
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.FollowingAlbumsCondition
import com.untilled.roadcapture.databinding.FragmentFollowingalbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemFollowingFilterBinding
import com.untilled.roadcapture.features.common.AlbumMorePopupMenu
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.followingalbums.dto.FollowingAlbumsAdapterArgs
import com.untilled.roadcapture.features.root.followingalbums.dto.FollowingFiltersViewHolderArgs
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingAlbumsFragment : Fragment() {

    private var _binding: FragmentFollowingalbumsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowingAlbumsViewModel by viewModels()

    private val filterAdapter: FollowingAlbumsFilterAdapter by lazy {
        FollowingAlbumsFilterAdapter(filterItemOnClickListener).apply {
            addLoadStateListener(headerLoadStateListener)
        }
    }

    private val adapter: FollowingAlbumsAdapter by lazy {
        FollowingAlbumsAdapter(
            FollowingAlbumsAdapterArgs(
                followingFiltersViewHolderArgs = FollowingFiltersViewHolderArgs(filterAdapter),
                itemOnClickListener = albumItemOnClickListener,
            )
        ).apply {
            addLoadStateListener { loadState ->
                binding.swipeFollowingalbumsInnercontainer.isRefreshing =
                    loadState.source.refresh is LoadState.Loading
            }
        }
    }

    private val notificationOnClickListener: (View?) -> Unit = {
        rootFrom3Depth().navigateToNotification()
    }

    private val filterItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        refresh((args?.item as ItemFollowingFilterBinding).user?.followingSortByAlbumId)
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
    }

    private val headerLoadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        if (loadState.source.refresh is LoadState.Loading) {
            binding.swipeFollowingalbumsInnercontainer.isRefreshing = true
        }
        if (loadState.source.refresh is LoadState.NotLoading) {
            binding.swipeFollowingalbumsInnercontainer.isRefreshing = false
            binding.constraintFollowingalbumsContainer.isVisible = true
        }
    }

    private val albumItemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

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
            R.id.img_ialbums_more -> AlbumMorePopupMenu(
                requireContext(),
                args.view,
                menuItemClickListener
            ).show()
        }
    }

    private val menuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popupmenu_albums_more_report -> {
                showReportDialog({})
            }
            R.id.popupmenu_albums_more_hide -> {

            }
            R.id.popupmenu_albums_more_share -> {

            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refresh()
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
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.swipeFollowingalbumsInnercontainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun observeData() {
        viewModel.load.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitData(
                    viewLifecycleOwner.lifecycle,
                    it.first.map { FollowingAlbumPagingItem.Data(it) as FollowingAlbumPagingItem }
                        .insertHeaderItem(item = it.second.let {
                            filterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                            FollowingAlbumPagingItem.Header(it)
                        })
                )
            } ?: kotlin.run {
                binding.constraintFollowingalbumsContainer.isVisible = false
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerFollowingalbums.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
    }

    private fun refresh(followingId: Long? = null) {
        viewModel.loadAll(FollowingAlbumsCondition(followingId))
    }

    private fun setOnClickListeners() {
        binding.imageFollowingalbumsNotification.setOnClickListener(notificationOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
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
        val animator = ValueAnimator.ofFloat(start, end).setDuration(500)
        animator.addUpdateListener {
            view.progress = it.animatedValue as Float
        }
        return animator
    }

}