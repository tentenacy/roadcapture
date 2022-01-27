package com.untilled.roadcapture.features.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ItemLoadingBinding

class PageLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PageLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState){
            binding.progressIloading.isVisible = loadState is LoadState.Loading
            binding.btnIloading.isVisible = loadState is LoadState.Error
            binding.btnIloading.setOnClickListener { retry() }
            binding.textIloading.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            binding.textIloading.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}