package com.untilled.roadcapture.features.root.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.jakewharton.rxbinding3.widget.textChanges
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.FragmentTitleSearchBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.utils.searchFrom1Depth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class TitleSearchFragment : BaseFragment() {

    private var _binding: FragmentTitleSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels({ requireParentFragment() })

    private val adapter: TitleSearchAdapter by lazy {
        TitleSearchAdapter(itemOnClickListener)
    }

    private val itemOnClickListener: (ItemClickArgs?) -> Unit = { args ->

    }

    private val albumObserver: (PagingData<Albums.Album>) -> Unit = { pagingData ->
        adapter.submitData(lifecycle, pagingData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initSearch()
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
    }

    private fun observeData() {
        viewModel.album.observe(viewLifecycleOwner, albumObserver)
        viewModel.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when (it.first) {
                    SearchViewModel.EVENT_INIT_SEARCH -> {
                        compositeDisposable.add(searchFrom1Depth().binding.edtSearchInput.textChanges()
                            .debounce(500, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.io())
                            .subscribe {
                                viewModel.getAlbums(AlbumsCondition(title = it.toString()))
                            })
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                searchFrom1Depth().binding.imgSearchNosearch.visibility = View.VISIBLE
                searchFrom1Depth().binding.textSearchNosearch1.visibility = View.VISIBLE
                searchFrom1Depth().binding.textSearchNosearch2.visibility = View.VISIBLE
            } else {
                searchFrom1Depth().binding.imgSearchNosearch.visibility = View.INVISIBLE
                searchFrom1Depth().binding.textSearchNosearch1.visibility =
                    View.INVISIBLE
                searchFrom1Depth().binding.textSearchNosearch2.visibility =
                    View.INVISIBLE
            }
        }
        binding.recyclerTitlesearch.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PageLoadStateAdapter { adapter.retry() },
            footer = PageLoadStateAdapter { adapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}