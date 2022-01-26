package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.rootFrom3Depth
import com.untilled.roadcapture.utils.navigateToFollower
import com.untilled.roadcapture.utils.navigateToFollowing
import com.untilled.roadcapture.utils.navigateToSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMystudioBinding? = null
    val binding get() = _binding!!

    private val viewModel: MyStudioViewModel by viewModels()

    private val myStudioAlbumsAdapter: MyStudioAlbumsAdapter by lazy{
        MyStudioAlbumsAdapter(itemOnClickListener)
    }
    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

    }

    private val albumsObserver: (PagingData<UserAlbums.UserAlbum>) -> Unit = { pagingData ->
        myStudioAlbumsAdapter.submitData(lifecycle, pagingData)
    }

    private val userInfoObserver: (StudioUserResponse) -> Unit = { user ->
        binding.user = user
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
        initViews()
        initAdapter()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.myAlbums.observe(viewLifecycleOwner, albumsObserver)
        viewModel.userInfo.observe(viewLifecycleOwner,userInfoObserver)
    }

    private fun initViews(){
        viewModel.getMyInfo()
    }

    private fun initAdapter() {
        binding.recyclerMystudioAlbum.adapter = myStudioAlbumsAdapter
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
//                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user))
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            rootFrom3Depth().navigateToSettings()
        }
    }
}