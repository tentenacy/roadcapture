package com.untilled.roadcapture.features.root.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemTitleSearchBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.albums.AlbumsAdapter
import com.untilled.roadcapture.features.root.albums.dto.LikeStatus

class TitleSearchAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<Albums.Album, TitleSearchAdapter.TitleSearchViewHolder>(
    COMPARATOR
) {
    inner class TitleSearchViewHolder(private val binding: ItemTitleSearchBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Albums.Album) {
            binding.album = album
            binding.setOnClickItem{ view ->
                itemOnClickListener(ItemClickArgs(binding,view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleSearchViewHolder {
        return TitleSearchViewHolder(
            ItemTitleSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TitleSearchViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Albums.Album>() {
            override fun areItemsTheSame(oldItem: Albums.Album, newItem: Albums.Album): Boolean {
                return oldItem.albumsId == newItem.albumsId
            }

            override fun areContentsTheSame(oldItem: Albums.Album, newItem: Albums.Album): Boolean {
                return oldItem == newItem
            }
        }
    }


}