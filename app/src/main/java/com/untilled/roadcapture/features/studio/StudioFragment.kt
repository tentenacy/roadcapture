package com.untilled.roadcapture.features.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentStudioBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToFollower
import com.untilled.roadcapture.utils.navigateToFollowing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioFragment : Fragment() {

    private var _binding: FragmentStudioBinding? = null
    val binding get() = _binding!!

    val args: StudioFragmentArgs by navArgs()
    private val viewModel: StudioViewModel by viewModels()

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

    }

    private val appbarOffsetChangedListener = AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        binding.swipeStudioContainer.isEnabled = verticalOffset == 0
    }

    private val studioAlbumsAdapter: StudioAlbumsAdapter by lazy{
        StudioAlbumsAdapter(itemOnClickListener)
    }

    private val userObserver = { user: StudioUserResponse ->
        binding.user = user
    }

    private val albumsObserver: (PagingData<UserAlbums.UserAlbum>) -> Unit = { pagingData ->
        studioAlbumsAdapter.submitData(lifecycle, pagingData)
    }

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refresh()
        binding.swipeStudioContainer.isRefreshing = false
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
        initViews()
        initAdapter()
        setOnClickListeners()
        setOnRefreshListener()
        addOnOffsetChangedListener()
    }

    private fun addOnOffsetChangedListener(){
        binding.appbarStudio.addOnOffsetChangedListener(appbarOffsetChangedListener)
    }

    private fun setOnRefreshListener(){
        binding.swipeStudioContainer.setOnRefreshListener(swipeRefreshListener)
    }
    private fun observeData() {
        viewModel.userInfo.observe(viewLifecycleOwner,userObserver)
        viewModel.albums.observe(viewLifecycleOwner,albumsObserver)
    }

    private fun initViews(){
        viewModel.getUserInfo(args.id)
    }

    private fun initAdapter() {
        binding.recyclerStudioAlbum.adapter = studioAlbumsAdapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter{studioAlbumsAdapter.retry()},
            footer = PageLoadStateAdapter{studioAlbumsAdapter.retry()}
        )
    }

    private fun refresh() {
        viewModel.getStudioAlbums(args.id,null)
    }


    private val btnStudioFollowObserver: (View?) -> Unit = {
        if(binding.user?.followed!!){
            viewModel.unfollow(args.id)
            binding.btnStudioFollow.text = "팔로우"
            binding.user?.followed = false
            binding.textStudioFollower.text = (binding.user?.followerCount!! - 1).toString()
            val temp = binding.user?.followerCount!!
            binding.user?.followerCount = temp - 1
        } else{
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