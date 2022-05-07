package com.untilled.roadcapture.features.picture

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.comment.AlbumCommentsAdapter
import com.untilled.roadcapture.features.common.CommentMorePopupMenu
import com.untilled.roadcapture.features.common.MyCommentMorePopupMenu
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject
import com.google.android.material.bottomsheet.BottomSheetBehavior

import android.widget.FrameLayout

import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import com.orhanobut.logger.Logger


@AndroidEntryPoint
class CommentBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetCommentBinding? = null
    val binding get() = _binding!!

    private val viewModel: PictureViewerViewModel by viewModels({ pictureViewerFrom2Depth() })

    @Inject
    lateinit var customDivider: CustomDivider

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.comments()
    }

    private val pictureCommentsAdapter: PictureCommentsAdapter by lazy {
        PictureCommentsAdapter(itemOnClickListener).apply {
            addLoadStateListener(loadStateListener)
        }
    }

    private val albumCommentsAdapter: AlbumCommentsAdapter by lazy {
        AlbumCommentsAdapter(itemOnClickListener).apply {
            addLoadStateListener(loadStateListener)
        }
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        binding.swipeBottomsheetCommentInnerContainer.isRefreshing =
            loadState.source.refresh is LoadState.Loading
        }

    private val albumCommentsObserver: (PagingData<AlbumComments.AlbumComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { albumCommentsAdapter.submitData(viewLifecycleOwner.lifecycle, it) }
        }

    private val pictureCommentsObserver: (PagingData<PictureComments.PictureComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { pictureCommentsAdapter.submitData(viewLifecycleOwner.lifecycle, it) }
        }

    private val currentPositionObserver: (Int) -> Unit = { position ->
        if (position == 0) {
            viewModel.albumComments.observe(viewLifecycleOwner, albumCommentsObserver)
            binding.recycleBottomsheetComment.adapter =
                albumCommentsAdapter.withLoadStateHeaderAndFooter(
                    header = PageLoadStateAdapter { albumCommentsAdapter.retry() },
                    footer = PageLoadStateAdapter { albumCommentsAdapter.retry() }
                )
            lifecycleScope.launchWhenCreated {
                albumCommentsAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect {
                        binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
                    }
            }
        } else {
            viewModel.pictureComments.observe(viewLifecycleOwner, pictureCommentsObserver)
            binding.recycleBottomsheetComment.adapter =
                pictureCommentsAdapter.withLoadStateHeaderAndFooter(
                    header = PageLoadStateAdapter { pictureCommentsAdapter.retry() },
                    footer = PageLoadStateAdapter { pictureCommentsAdapter.retry() }
                )
            lifecycleScope.launchWhenCreated {
                pictureCommentsAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect {
                        binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
                    }
            }
        }
    }

    private val menuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popup_menu_comment_more_report -> {
                showReportDialog({})
            }
            R.id.popup_menu_mycomment_more_edit -> {

            }
            R.id.popup_menu_mycomment_more_del -> {

            }
        }
        true
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        val userId = (args?.item as ItemCommentBinding).comments!!.user.id
        when (args.view?.id) {
            R.id.img_icomment_more -> {
                if (userId == User.id) MyCommentMorePopupMenu(
                    requireContext(),
                    args.view,
                    menuItemClickListener
                ).show()
                else CommentMorePopupMenu(requireContext(), args.view, menuItemClickListener).show()
            }
            R.id.img_icomment_profile -> {
                pictureViewerFrom2Depth().navigateToStudio(userId)
            }
        }
    }

    private val postOnClickListener: (View?) -> Unit = {
        viewModel.postComment(CommentCreateRequest(binding.edtBottomsheetCommentInput.text.toString()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.comments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetCommentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initViews()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.swipeBottomsheetCommentInnerContainer.setOnRefreshListener(swipeRefreshListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

//        viewModel.clearComments()

        _binding = null
    }

    private fun observeData() {
        viewModel.currentPosition.observe(viewLifecycleOwner, currentPositionObserver)
    }

    private fun setOnClickListeners() {
        binding.imgBottomsheetCommentInput.setOnClickListener(postOnClickListener)
    }

    private fun initViews() {
        binding.recycleBottomsheetComment.addItemDecoration(customDivider)
        expandFullHeight()
    }

}