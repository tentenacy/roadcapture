package com.untilled.roadcapture.features.follower

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.databinding.FragmentFollowerBinding
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.hideKeyboard
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToStudio
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowersFragment : Fragment(){

    private var _binding: FragmentFollowerBinding? = null
    val binding get() = _binding!!
    private val viewModel: FollowersViewModel by viewModels()
    private val args: FollowersFragmentArgs by navArgs()
    private val adapter: FollowersAdapter by lazy {
        FollowersAdapter(itemOnClickListener)
    }

    @Inject
    lateinit var customDivider: CustomDivider

    private val swipeRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.getUserFollower(args.id)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.btn_ifollow -> viewModel.follow((args.item as ItemFollowBinding).user!!.followingId)

            R.id.img_ifollow_profile -> navigateToStudio((args.item as ItemFollowBinding).user!!.followingId)
        }
    }

    private val followersObserver: (PagingData<Followers.Follower>) -> Unit = { pagingData ->
        adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
    }

    private val editorActionListener: (TextView?, Int?, KeyEvent) -> Boolean = { v: TextView?, actionId: Int?, event: KeyEvent ->
        when(actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                mainActivity().hideKeyboard(binding.edtFollowerInput)
                true
            }
            else ->  false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.getUserFollower(args.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)



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
        binding.swipeFollowerInnercontainer.setOnRefreshListener(swipeRefreshListener)
    }

    private fun observeData() {
        viewModel.user.observe(viewLifecycleOwner, followersObserver)
    }

    private fun initAdapter(){
        binding.recyclerFollower.addItemDecoration(customDivider)
        binding.recyclerFollower.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter{adapter.retry()},
            footer = PageLoadStateAdapter{adapter.retry()}
        )
        adapter.addLoadStateListener { loadState ->
            binding.swipeFollowerInnercontainer.isRefreshing = loadState.source.refresh is LoadState.Loading
        }
    }

    private fun setOnClickListeners() {
        binding.imageFollowerBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.edtFollowerInput.setOnEditorActionListener(editorActionListener)
    }

}