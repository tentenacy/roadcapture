package com.untilled.roadcapture.features.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentStudioBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.studio.UserAlbumItem
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToFollower
import com.untilled.roadcapture.utils.navigateToFollowing
import com.untilled.roadcapture.utils.showReportDialog
import dagger.hilt.android.AndroidEntryPoint

//TODO: 스튜디오 유저가 현재 로그인된 유저인지 확인하여 화면을 다르게 보여준다.
@AndroidEntryPoint
class StudioFragment : Fragment() {

    private var _binding: FragmentStudioBinding? = null
    val binding get() = _binding!!

    val args: StudioFragmentArgs by navArgs()
    private val viewModel: StudioViewModel by viewModels()

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when (args?.view?.id) {
            R.id.img_ialbums_studio_more -> {
                val popupMenu = PopupMenu(requireContext(), args.view)
                popupMenu.apply {
                    menuInflater.inflate(R.menu.popupmenu_studio_more, popupMenu.menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.popupmenu_studio_more_share -> {
                            }
                            R.id.popupmenu_studio_more_report -> {
                                showReportDialog({})
                            }
                        }
                        true
                    }
                }.show()
            }
        }
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        binding.swipeStudioContainer.isRefreshing =
            loadState.source.refresh is LoadState.Loading
        if (loadState.source.refresh is LoadState.NotLoading) {
            binding.coordinatorStudioContainer.isVisible = true
        }
    }

    private val appbarOffsetChangedListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.swipeStudioContainer.isEnabled = verticalOffset == 0
        }

    private val adapter: StudioAdapter by lazy {
        StudioAdapter(itemOnClickListener)
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getStudioAlbums(args.id, null)
        binding.swipeStudioContainer.isRefreshing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.loadAll(args.id, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudioBinding.inflate(layoutInflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.swipeStudioContainer.setOnRefreshListener(swipeRefreshListener)
        binding.appbarStudio.addOnOffsetChangedListener(appbarOffsetChangedListener)
    }

    private fun observeData() {
        viewModel.load.observe(viewLifecycleOwner) {
            it?.let {
                binding.user = it.first
                adapter.submitData(
                    lifecycle,
                    it.second.map { UserAlbumItem.Data(it) as UserAlbumItem }
                        .insertHeaderItem(item = UserAlbumItem.Header))
            } ?: kotlin.run {
                binding.coordinatorStudioContainer.isVisible = false
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerStudioAlbum.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener(loadStateListener)
    }

    private val btnStudioFollowObserver: (View?) -> Unit = {
        if (binding.user?.followed!!) {
            viewModel.unfollow(args.id)
            binding.btnStudioFollow.text = "팔로우"
            binding.user?.followed = false
            binding.textStudioFollower.text = (binding.user?.followerCount!! - 1).toString()
            val temp = binding.user?.followerCount!!
            binding.user?.followerCount = temp - 1
        } else {
            viewModel.follow(args.id)
            binding.btnStudioFollow.text = "언팔로우"
            binding.user?.followed = true
            binding.textStudioFollower.text = (binding.user?.followerCount!! + 1).toString()
            val temp = binding.user?.followerCount!!
            binding.user?.followerCount = temp + 1
        }
    }

    private fun setOnClickListeners() {
        binding.textStudioFollower.setOnClickListener {
            navigateToFollower(args.id)
        }
        binding.textStudioFollowing.setOnClickListener {
            navigateToFollowing(args.id)
        }
        binding.btnStudioFollow.setOnClickListener(btnStudioFollowObserver)
        binding.imgStudioMoreBefore.setOnClickListener {

        }
        binding.imgStudioMoreAfter.setOnClickListener {

        }
        binding.imgStudioBackAfter.setOnClickListener {
            mainActivity().onBackPressed()
        }
        binding.imgStudioBackBefore.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }
}