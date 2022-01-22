package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.utils.rootFromChild
import com.untilled.roadcapture.utils.navigateToFollower
import com.untilled.roadcapture.utils.navigateToFollowing
import com.untilled.roadcapture.utils.navigateToSettings
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyStudioFragment : Fragment() {

    private var _binding: FragmentMystudioBinding? = null
    val binding get() = _binding!!

    private val viewModel: MyStudioViewModel by viewModels()

    @Inject
    lateinit var userAlbumsAdapter: UserAlbumsAdapter

    private val userAlbumsObserver: (PagingData<UserAlbums.UserAlbum>) -> Unit = { pagingData ->
        userAlbumsAdapter.submitData(lifecycle, pagingData)
    }
    private val userInfoObserver: (UsersResponse) -> Unit = { user ->
        binding.user = user
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMystudioBinding.inflate(layoutInflater, container, false)

        binding.recyclerMystudioAlbum.adapter = userAlbumsAdapter

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
        viewModel.userAlbums.observe(viewLifecycleOwner, userAlbumsObserver)
        viewModel.userInfo.observe(viewLifecycleOwner,userInfoObserver)
    }

    private fun initViews(){
        viewModel.getUserInfo(User.id)
    }

    private fun initAdapter() {
        refresh()
    }

    private fun refresh() {
        viewModel.getUserAlbums()
    }

    private fun setOnClickListeners() {
        binding.textMystudioFollower.setOnClickListener {
            rootFromChild().navigateToFollower(User.id)
        }
        binding.textMystudioFollowing.setOnClickListener {
            rootFromChild().navigateToFollowing(User.id)
        }
        binding.btnMystudioEdit.setOnClickListener {
//            Navigation.findNavController(rootFragmentFrom3Depth().binding.root)
//                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user))
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            rootFromChild().navigateToSettings()
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            rootFromChild().navigateToSettings()
        }
    }
}