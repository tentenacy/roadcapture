package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TODO: 로딩 중 onDestroyView 호출 후 onCreateView 진입 시 NPE 버그 수정
@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMystudioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyStudioViewModel by viewModels()

    private val adapter: MyStudioAdapter by lazy {
        MyStudioAdapter(itemOnClickListener)
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit = { loadState ->
        Logger.d("isRefreshing = ${loadState.source.refresh is LoadState.Loading}")
        binding.swipeMystudioContainer.isRefreshing =
            loadState.source.refresh is LoadState.Loading
        if (loadState.source.refresh is LoadState.NotLoading) {
            binding.coordinatorMystudioContainer.isVisible = true
        }
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when (args?.view?.id) {
            R.id.img_ialbums_studio_more -> MyStudioMorePopupMenu(
                requireContext(),
                args.view,
                menuItemClickListener((args?.item as ItemAlbumsStudioBinding).album?.userAlbumId!!)
            ).show()
        }
    }

    private fun menuItemClickListener(albumId: Long): (item: MenuItem) -> Boolean = { item ->
        when (item.itemId) {
            R.id.popupmenu_mystudio_more_share -> {

            }
            R.id.popupmenu_mystudio_more_edit -> {

            }
            R.id.popupmenu_mystudio_more_del -> {
                viewModel.deleteAlbum(albumId)
            }
        }
        true
    }

    private val appbarOffsetChangedListener =
        AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            binding.swipeMystudioContainer.isEnabled = verticalOffset == 0
        }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.loadAll(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadAll(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMystudioBinding.inflate(layoutInflater, container, false)

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
        binding.swipeMystudioContainer.setOnRefreshListener(swipeRefreshListener)
        binding.appbarMystudio.addOnOffsetChangedListener(appbarOffsetChangedListener)
    }

    private fun observeData() {
        viewModel.load.observe(viewLifecycleOwner) {
            it?.let {
                binding.user = it.first
                adapter.submitData(viewLifecycleOwner.lifecycle, it.second.map { UserAlbumItem.Data(it) as UserAlbumItem }
                    .insertHeaderItem(item = UserAlbumItem.Header))
            } ?: kotlin.run {
                binding.coordinatorMystudioContainer.isVisible = false
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerMystudioAlbum.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener(loadStateListener)
    }

    private fun setOnClickListeners() {
        binding.textMystudioFollower.setOnClickListener {
            rootFrom3Depth().navigateToFollower(User.id)
        }
        binding.textMystudioFollowing.setOnClickListener {
            rootFrom3Depth().navigateToFollowing(User.id)
        }
        binding.btnMystudioEdit.setOnClickListener {
//            Navigation.findNavController(rootFragmentFrom3Depth().binding.root)
//                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user)
            showSnackbar(requireView(), "스낵바 테스트")
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
    }
}