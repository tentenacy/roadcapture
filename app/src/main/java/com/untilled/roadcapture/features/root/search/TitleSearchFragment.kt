package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.FragmentTitleSearchBinding
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleSearchFragment : Fragment() {

    private var _binding: FragmentTitleSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels({ requireParentFragment() })

    private val adapter: TitleSearchAdapter by lazy{
        TitleSearchAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

    }

    private val albumObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        adapter.submitData(lifecycle, pagingData)
    }

    private val searchObserver: (String?) -> Unit = {
        refresh(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTitleSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        setOnClickListeners()
    }
    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner, albumObserver)
        viewModel.search.observe(viewLifecycleOwner,searchObserver)
    }

    private fun initAdapter() {
        binding.recyclerTitlesearch.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter{adapter.retry()},
            footer = PageLoadStateAdapter{adapter.retry()}
        )
    }


    private fun setOnClickListeners() {

    }

    fun refresh(title: String?) {
        viewModel.getAlbums(AlbumsCondition(title = title))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}