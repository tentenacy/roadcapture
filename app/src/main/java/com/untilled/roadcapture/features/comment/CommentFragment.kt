package com.untilled.roadcapture.features.comment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.datasource.api.dto.comment.Comments
import com.untilled.roadcapture.databinding.FragmentCommentBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.features.root.albums.AlbumsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CommentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarComment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CommentFragmentArgs by navArgs()
        initAdapter(args.albumsId)
        setOnClickListeners()
        viewModel.albumComments.observe(viewLifecycleOwner) { pagingData ->
            //TODO: fetch
        }
    }

    private fun setOnClickListeners() {
        binding.imgCommentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(albumId: Long) {
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recyclerComment.addItemDecoration(customDivider)
        viewModel.getAlbumComments(albumId)
    }

    private fun showReportDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dlg_report, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgreport_confirm)?.setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.dlgreport_cancel)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}