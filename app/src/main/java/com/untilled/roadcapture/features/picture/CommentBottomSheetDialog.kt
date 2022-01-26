package com.untilled.roadcapture.features.picture

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
import com.untilled.roadcapture.features.comment.AlbumCommentsAdapter
import com.untilled.roadcapture.features.common.ReportDialogFragment
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.mainActivity
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
        }

    private val pictureCommentsObserver: (PagingData<PictureComments.PictureComment>?) -> Unit =
        { pagingData ->
            pagingData?.let { pictureCommentsAdapter.submitData(lifecycle, it) }
        }

    private val currentPositionObserver: (Int) -> Unit = { position ->
        if (position == 0) {
            binding.recycleBottomsheetComment.adapter = albumCommentsAdapter
        } else {
            binding.recycleBottomsheetComment.adapter = pictureCommentsAdapter
        }
        viewModel.getComments()
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when (args?.view?.id) {
            R.id.img_icomment_more -> {
                val popupMenu = PopupMenu(requireContext(), args.view)
                popupMenu.apply {
                    menuInflater.inflate(R.menu.popupmenu_comment_more, popupMenu.menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.popup_menu_comment_more_report -> {
                                showReportDialog()
                            }
                        }
                        true
                    }
                }.show()
            }
            R.id.img_icomment_profile -> {
                //TODO: parent의 parent인 pictureViewerFragment에서 studioFragment로 이동
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_commentFragment_to_studioFragment)
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

    private fun showReportDialog() {
        ReportDialogFragment({}).show(childFragmentManager, DialogTagConstant.REPORT_DIALOG)
    }

}