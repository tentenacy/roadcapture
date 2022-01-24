package com.untilled.roadcapture.features.picture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
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

    private val adapter: CommentBottomSheetAdapter by lazy {
        CommentBottomSheetAdapter(itemOnClickListener)
    }

    private val albumCommentsObserver: (PagingData<AlbumComments.AlbumComment>) -> Unit =
        { pagingData ->
            // adapter.submitData(lifecycle, pagingData)
        }
    private val pictureCommentsObserver: (PagingData<PictureComments.PictureComment>) -> Unit =
        { pagingData ->
            adapter.submitData(lifecycle, pagingData)
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
        initAdapter()
        expandFullHeight()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        viewModel.albumComments.observe(viewLifecycleOwner, albumCommentsObserver)
        viewModel.pictureComments.observe(viewLifecycleOwner, pictureCommentsObserver)
        viewModel.currentPosition.observe(viewLifecycleOwner) { position ->
            if (position == -1) {
                viewModel.getAlbumComments()
            } else {
                viewModel.getPictureComments(position)
            }
        }
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners() {
        binding.imgBottomsheetCommentBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }

    private fun initAdapter() {
        binding.recycleBottomsheetComment.addItemDecoration(customDivider)
        binding.recycleBottomsheetComment.adapter = adapter
    }

    private fun showReportDialog() {
        ReportDialogFragment({}).show(childFragmentManager, DialogTagConstant.REPORT_DIALOG)
    }
}