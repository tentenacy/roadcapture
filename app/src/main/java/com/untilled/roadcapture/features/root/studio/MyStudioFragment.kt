package com.untilled.roadcapture.features.root.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.FragmentMystudioBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
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
    }

    private fun initViews(){
    }

    private fun initAdapter() {
        refresh()
    }

    fun refresh() {
        viewModel.getUserAlbums()
    }

    private fun setOnClickListeners() {
        binding.textMystudioFollower.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToFollowerFragment(User.id))
        }
        binding.textMystudioFollowing.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(RootFragmentDirections.actionRootFragmentToFollowingFragment(User.id))
        }
        binding.btnMystudioEdit.setOnClickListener {
//            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
//                .navigate(RootFragmentDirections.actionRootFragmentToMyStudioModification(binding.user))
        }
        binding.imageMystudioSettingBefore.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
        binding.imageMystudioSettingAfter.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
    }
}