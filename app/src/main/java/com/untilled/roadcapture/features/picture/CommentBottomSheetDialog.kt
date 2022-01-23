package com.untilled.roadcapture.features.picture

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.BottomsheetCommentBinding
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
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
        updateView(viewModel.currentPosition - 1)
    }


    private fun updateView(position: Int) {
        when (position) {
            -1 -> viewModel.getAlbumComments(viewModel.album.value!!.id)
            else -> viewModel.getPictureComments(viewModel.album.value!!.pictures?.get(position)!!.id)
        }
    }


    private fun showReportDialog() {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dlg_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewReport = dialogView.findViewById<TextView>(R.id.text_dlgreport_confirm)
        val textViewCancel = dialogView.findViewById<TextView>(R.id.dlgreport_cancel)

        textViewReport?.setOnClickListener {
            dialog.dismiss()
        }
        textViewCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}