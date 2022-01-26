package com.untilled.roadcapture.features.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.comment.AlbumCommentsAdapter
import com.untilled.roadcapture.features.common.CommentMorePopupMenu
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class CommentBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetCommentBinding? = null
    val binding get() = _binding!!

    private val viewModel: PictureViewerViewModel by viewModels({ pictureViewerFrom2Depth() })

    @Inject
    lateinit var customDivider: CustomDivider

    private val pictureCommentsAdapter: PictureCommentsAdapter by lazy {
        PictureCommentsAdapter(itemOnClickListener)
    }

    private val albumCommentsAdapter: AlbumCommentsAdapter by lazy {
        AlbumCommentsAdapter(itemOnClickListener)
    }

    private val albumCommentsObserver: (PagingData<AlbumComments.AlbumComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { albumCommentsAdapter.submitData(lifecycle, it) }
        }

    private val pictureCommentsObserver: (PagingData<PictureComments.PictureComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { pictureCommentsAdapter.submitData(lifecycle, it) }
        }

    private val currentPositionObserver: (Int) -> Unit = { position ->
        if (position == 0) {
            binding.recycleBottomsheetComment.adapter = albumCommentsAdapter
            lifecycleScope.launchWhenCreated {
                albumCommentsAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect {
                        binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
                    }
            }
        } else {
            binding.recycleBottomsheetComment.adapter = pictureCommentsAdapter
            lifecycleScope.launchWhenCreated {
                pictureCommentsAdapter.loadStateFlow
                    .distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect {
                        binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
                    }
            }
        }
        viewModel.getComments()
    }

    private val menuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popup_menu_comment_more_report -> {
                showReportDialog({})
            }
        }
        true
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        val userId = (args?.item as ItemCommentBinding).comments!!.user.id
        when (args.view?.id) {
            R.id.img_icomment_more -> {
                CommentMorePopupMenu(requireContext(), args.view, menuItemClickListener).show()
            }
            R.id.img_icomment_profile -> {
                pictureViewerFrom2Depth().navigateToStudio(userId)
            }
        }
    }

    private val postOnClickListener: (View?) -> Unit = {
        viewModel.postComment(CommentCreateRequest(binding.edtBottomsheetCommentInput.text.toString()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetCommentBinding.inflate(inflater, container, false)

        mainActivity().setSupportActionBar(binding.toolbarBottomsheetComment)
        binding.coordinatorBottomsheetCommentContainer.setBottomSheetDialogPadding(mainActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initViews()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.clearComments()

        _binding = null
    }

    private fun observeData() {
        viewModel.albumComments.observe(viewLifecycleOwner, albumCommentsObserver)
        viewModel.pictureComments.observe(viewLifecycleOwner, pictureCommentsObserver)
        viewModel.currentPosition.observe(viewLifecycleOwner, currentPositionObserver)
    }

    private fun setOnClickListeners() {
        binding.imgBottomsheetCommentBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.imgBottomsheetCommentInput.setOnClickListener(postOnClickListener)
    }

    private fun initViews() {
        expandFullHeight()
        binding.recycleBottomsheetComment.addItemDecoration(customDivider)
    }

}