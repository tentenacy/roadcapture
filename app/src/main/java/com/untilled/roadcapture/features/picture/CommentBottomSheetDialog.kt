package com.untilled.roadcapture.features.picture

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
import javax.inject.Inject

@AndroidEntryPoint
class CommentBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetCommentBinding? = null
    val binding get() = _binding!!

    private val viewModel: PictureViewerViewModel by viewModels({ requireParentFragment().requireParentFragment() })

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
            binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
        }

    private val pictureCommentsObserver: (PagingData<PictureComments.PictureComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { pictureCommentsAdapter.submitData(lifecycle, it) }
            binding.recycleBottomsheetComment.scrollToPositionSmooth(0)
        }

    private val currentPositionObserver: (Int) -> Unit = { position ->
        if (position == 0) {
            binding.recycleBottomsheetComment.adapter = albumCommentsAdapter
            initPagingLoadStateFlow(albumCommentsAdapter, binding.recycleBottomsheetComment)
        } else {
            binding.recycleBottomsheetComment.adapter = pictureCommentsAdapter
            initPagingLoadStateFlow(pictureCommentsAdapter, binding.recycleBottomsheetComment)
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
        val userId = (args?.item as ItemCommentBinding).comments?.user?.id
        when (args.view?.id) {
            R.id.img_icomment_more -> {
                CommentMorePopupMenu(requireContext(), args.view, menuItemClickListener).show()
            }
            R.id.img_icomment_profile -> {
                userId?.let { pictureViewerFrom2Depth().navigateToStudio(it) }
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initRecyclerView()
        expandFullHeight()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.clearComments()

        _binding = null
    }

    private fun expandFullHeight() {
        val bottomSheet = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 95 / 100
    }

    private fun getWindowHeight(): Int {
        val outMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = activity?.display
            display?.getRealMetrics (outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity?.windowManager?.defaultDisplay
            @Suppress("DEPRECATION")
            display?.getMetrics(outMetrics)
        }
        Log.d("Test",outMetrics.heightPixels.toString())
        return outMetrics.heightPixels
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

    private fun initRecyclerView() {
        binding.recycleBottomsheetComment.addItemDecoration(customDivider)
    }

}