package com.untilled.roadcapture.features.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.databinding.FragmentCommentBinding
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.common.CommentMorePopupMenu
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.ReportDialogFragment
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToStudio
import com.untilled.roadcapture.utils.showReportDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    val binding get() = _binding!!

    private val viewModel: CommentViewModel by viewModels()
    private val args: CommentFragmentArgs by navArgs()

    private val adapterAlbum: AlbumCommentsAdapter by lazy {
        AlbumCommentsAdapter(itemClickListener)
    }

    @Inject
    lateinit var customDivider: CustomDivider

    private val commentObserver: (PagingData<AlbumComments.AlbumComment>) -> Unit = { pagingData ->
        adapterAlbum.submitData(lifecycle, pagingData)
    }

    private val menuItemClickListener: (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popup_menu_comment_more_report -> {
                showReportDialog({})
            }
        }
        true
    }


    private val itemClickListener: (ItemClickArgs?) -> Unit = { args ->
        val userId = (args?.item as ItemCommentBinding).comments!!.user.id

        when (args.view?.id) {
            R.id.img_icomment_more -> {
                CommentMorePopupMenu(requireContext(), args.view, menuItemClickListener).show()
            }
            R.id.img_icomment_profile -> {
                navigateToStudio(userId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getAlbumComments(args.albumsId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(layoutInflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)
        mainActivity().setSupportActionBar(binding.toolbarComment)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        initAdapter()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.albumComments.observe(viewLifecycleOwner, commentObserver)
    }

    private fun setOnClickListeners() {
        binding.imgCommentBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.imgCommentInput.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        binding.recyclerComment.addItemDecoration(customDivider)
        binding.recyclerComment.adapter = adapterAlbum.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter{adapterAlbum.retry()},
            footer = PageLoadStateAdapter{adapterAlbum.retry()}
        )
    }
}