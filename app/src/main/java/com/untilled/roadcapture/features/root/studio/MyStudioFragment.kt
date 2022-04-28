package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.insertHeaderItem
import androidx.paging.map
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMystudioBinding? = null
    val binding get() = _binding!!

    private val viewModel: MyStudioViewModel by viewModels()

    private val myStudioAdapter: MyStudioAdapter by lazy {
        MyStudioAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.img_ialbums_studio_more -> MyStudioMorePopupMenu(requireContext(), args.view, menuItemClickListener((args?.item as ItemAlbumsStudioBinding).album?.userAlbumId!!)).show()
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

    private val appbarOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        binding.swipeMystudioContainer.isEnabled = verticalOffset == 0
    }

    private val albumsObserver: (PagingData<UserAlbums.UserAlbum>) -> Unit = { pagingData ->
        myStudioAdapter.submitData(lifecycle, pagingData.map { UserAlbumItem.Data(it) as UserAlbumItem }
            .insertHeaderItem(item = UserAlbumItem.Header))
    }

    private val userInfoObserver: (StudioUserResponse) -> Unit = { user ->
        binding.user = user
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
        binding.swipeMystudioContainer.isRefreshing = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMyInfo()
        refresh()
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
        setOnRefreshListener()
        addOnOffsetChangedListener()
    }

    private fun addOnOffsetChangedListener(){
        binding.appbarMystudio.addOnOffsetChangedListener(appbarOffsetChangedListener)
    }

    private fun setOnRefreshListener(){
        binding.swipeMystudioContainer.setOnRefreshListener(swipeRefreshListener)
    }
    private fun observeData() {
        viewModel.myAlbums.observe(viewLifecycleOwner, albumsObserver)
        viewModel.userInfo.observe(viewLifecycleOwner, userInfoObserver)
    }

    private fun initAdapter() {
        binding.recyclerMystudioAlbum.adapter = myStudioAdapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter{myStudioAdapter.retry()},
            footer = PageLoadStateAdapter{myStudioAdapter.retry()}
        )
    }

    private fun refresh() {
        viewModel.getMyStudioAlbums(null)
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
            showSnackbar(requireView(),"스낵바 테스트")
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
    }
}