package com.untilled.roadcapture.features.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentCreateRequest
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.databinding.FragmentCommentBinding
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.common.CommentMorePopupMenu
import com.untilled.roadcapture.features.common.MyCommentMorePopupMenu
import com.untilled.roadcapture.features.common.OnMenuItemClickListenerWithId
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToStudio
import com.untilled.roadcapture.utils.showReportDialog
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    val binding get() = _binding!!

    private val viewModel: CommentViewModel by viewModels()
    private val args: CommentFragmentArgs by navArgs()

    private val adapter: AlbumCommentsAdapter by lazy {
        AlbumCommentsAdapter(itemClickListener).apply {
            addLoadStateListener(loadStateListener)
        }
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        binding.swipeCommentInnercontainer.isRefreshing =
            loadState.source.refresh is LoadState.Loading

        if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
            binding.textCommentNocomment1.visibility = View.VISIBLE
            binding.textCommentNocomment2.visibility = View.VISIBLE
            binding.imgCommentNocomment.visibility = View.VISIBLE
            binding.recyclerComment.visibility = View.INVISIBLE
        } else {
            binding.textCommentNocomment1.visibility = View.INVISIBLE
            binding.textCommentNocomment2.visibility = View.INVISIBLE
            binding.imgCommentNocomment.visibility = View.INVISIBLE
            binding.recyclerComment.visibility = View.VISIBLE
        }
    }

    @Inject
    lateinit var customDivider: CustomDivider

    private val commentObserver: (PagingData<AlbumComments.AlbumComment>) -> Unit = { pagingData ->
        adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
    }

    private val menuItemClickListener: (commentId: Long, item: MenuItem?) -> Boolean =
        { commentId, item ->
            when (item?.itemId) {
                R.id.popup_menu_comment_more_report -> {
                    showReportDialog {}
                }
                R.id.popup_menu_mycomment_more_edit -> {

                }
                R.id.popup_menu_mycomment_more_del -> {
                    viewModel.delete(commentId)
                }
            }
            true
        }

    private val itemClickListener: (ItemClickArgs?) -> Unit = { args ->
        val userId = (args?.item as ItemCommentBinding).comments!!.user.id
        val commentId = args.item.comments!!.id

        when (args.view?.id) {
            R.id.img_icomment_more -> {
                if (userId == User.id) MyCommentMorePopupMenu(
                    requireContext(),
                    args.view,
                    OnMenuItemClickListenerWithId(commentId, menuItemClickListener)
                ).show()
                else CommentMorePopupMenu(
                    requireContext(),
                    args.view,
                    OnMenuItemClickListenerWithId(commentId, menuItemClickListener)
                ).show()
            }
            R.id.img_icomment_profile -> {
                navigateToStudio(userId)
            }
        }
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
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
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.swipeCommentInnercontainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun refresh() {
        viewModel.albumComments(args.albumId)
    }

    private fun observeData() {
        viewModel.albumComments.observe(viewLifecycleOwner, commentObserver)
        viewModel.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when(it.first) {
                    CommentViewModel.EVENT_REFRESH -> {
                        refresh()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imgCommentBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.imgCommentInput.setOnClickListener {
            viewModel.post(args.thumbnailPictureId, CommentCreateRequest(binding.edtCommentInput.text.toString()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter() {
        binding.recyclerComment.addItemDecoration(customDivider)
        binding.recyclerComment.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
    }
}