package com.untilled.roadcapture.features.root.comment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.features.root.albums.PictureViewerViewModel
import com.untilled.roadcapture.features.root.albums.dto.EpoxyItemArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentBottomSheetDialog : BottomSheetDialogFragment(){
    private var _binding: BottomsheetCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureViewerViewModel by viewModels({requireParentFragment().requireParentFragment()})
    private val epoxyController = CommentsEpoxyController()

    private val epoxyItemClickListener: (EpoxyItemArgs) -> Unit = { args ->
        when (args.clickedView.id) {
            R.id.imageview_item_comment_more -> {
                val popupMenu = PopupMenu(requireContext(), args.clickedView)
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
            R.id.imageview_item_comment_profile -> {
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
        _binding = BottomsheetCommentBinding.inflate(inflater,container,false)
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarBottomsheetComment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter(viewModel.currentPosition)
        expandFullHeight()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners() {
        binding.imgBottomsheetCommentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initAdapter(position: Int) {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recycleBottomsheetComment.addItemDecoration(customDivider)
        epoxyController.setOnClickListener(epoxyItemClickListener)
        updateView(position - 1)
        binding.recycleBottomsheetComment.setController(epoxyController)
    }

    private fun updateView(position: Int) {
        when(position){
            -1 -> {
                lifecycleScope.launch {
                    viewModel.getAlbumComments(token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY0MTYyNzIyNywiZXhwIjoxNjQxNjMwODI3fQ.qT8yBAYnciGhJGREpJlJDnARO5RnbPstc2E2WoZSWpc",viewModel.albumResponse.value!!.id)
                        .collectLatest { pagingData: PagingData<Comments> ->
                            epoxyController.submitData(pagingData)
                        }
                }
            }
            else -> {
                val pictureId = viewModel.albumResponse.value?.pictureResponses?.get(position)!!.id
                lifecycleScope.launch {
                    viewModel.getPictureComments(token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY0MTYyNzIyNywiZXhwIjoxNjQxNjMwODI3fQ.qT8yBAYnciGhJGREpJlJDnARO5RnbPstc2E2WoZSWpc",pictureId).collectLatest { pagingData: PagingData<Comments> ->
                        epoxyController.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun showReportDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dlg_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewReport = dialogView.findViewById<TextView>(R.id.textview_report_report)
        val textViewCancel = dialogView.findViewById<TextView>(R.id.textview_report_cancel)

        textViewReport?.setOnClickListener {
            dialog.dismiss()
        }
        textViewCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}