package com.untilled.roadcapture.features.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.databinding.ItemPagingLoadingBinding

class LoadStateViewHolder(private val binding: ItemPagingLoadingBinding, private val retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnIpagingloading.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState){

        binding.progressIpagingloading.isVisible = loadState is LoadState.Loading

        binding.btnIpagingloading.isVisible = loadState is LoadState.Error
        binding.textIpagingloading.text = (loadState as? LoadState.Error)?.error?.message

        binding.textIpagingloading.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
    }
}

class PageLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(ItemPagingLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false), retry)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}