package com.untilled.roadcapture.features.following

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
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.FragmentFollowingBinding
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.utils.ui.CustomDivider
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.hideKeyboard
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToStudio
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowingsFragment : Fragment(){

    private var _binding: FragmentFollowingBinding? = null
    val binding get() = _binding!!
    private val viewModel: FollowingsViewModel by viewModels()
    private val args: FollowingsFragmentArgs by navArgs()
    private val adapter: FollowingsAdapter by lazy {
        FollowingsAdapter(itemOnClickListener)
    }

    @Inject
    lateinit var customDivider: CustomDivider

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
                mainActivity().hideKeyboard(binding.edtFollowingInput)
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
        binding.recyclerFollowing.addItemDecoration(customDivider)
        binding.recyclerFollowing.adapter = adapter
    }

    private fun setOnClickListeners(){
        binding.imageFollowingBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.edtFollowingInput.setOnEditorActionListener (editorActionListener)
    }

}