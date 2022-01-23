package com.untilled.roadcapture.features.following

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.FragmentFollowingBinding
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import com.untilled.roadcapture.utils.hideKeyboard
import com.untilled.roadcapture.utils.navigateToStudio
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowingsFragment : Fragment(){

    private var _binding: FragmentFollowingBinding? = null
    val binding get() = _binding!!
    private val viewModel: FollowingsViewModel by viewModels()
    private val args: FollowingsFragmentArgs by navArgs()
    @Inject lateinit var adapter: FollowingsAdapter

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->
        when(args?.view?.id){
            R.id.btn_ifollow -> viewModel.follow((args.item as ItemFollowBinding).user!!.followingId)
            R.id.img_ifollow_profile -> navigateToStudio((args.item as ItemFollowBinding).user!!.followingId)
        }
    }

    private val userObserver: (PagingData<Followings.Following>) -> Unit = { pagingData ->
        adapter.submitData(lifecycle, pagingData)
    }

    private val editorActionListener: (TextView?, Int?, KeyEvent) -> Boolean = { v: TextView?, actionId: Int?, event: KeyEvent ->
        when(actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                requireActivity().hideKeyboard(binding.edtFollowingInput)
                true
            }
            else ->  false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        refresh()
        initAdapter()
        setOnClickListeners()
    }

    private fun observeData(){
        viewModel.user.observe(viewLifecycleOwner,userObserver)
    }

    private fun refresh(){
        viewModel.getUserFollowing(args.id)
    }


    private fun initAdapter(){
        val customDivider = CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
        binding.recyclerFollowing.addItemDecoration(customDivider)
        adapter.itemOnClickListener = itemOnClickListener
        binding.recyclerFollowing.adapter = adapter
    }

    private fun setOnClickListeners(){
        binding.imageFollowingBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.edtFollowingInput.setOnEditorActionListener (editorActionListener)
    }

}