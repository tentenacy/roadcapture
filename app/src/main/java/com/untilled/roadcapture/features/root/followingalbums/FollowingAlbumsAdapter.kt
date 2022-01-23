package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.albums.dto.LikeStatus

class FollowingAlbumsAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<Albums.Album, FollowingAlbumsAdapter.FollowingAlbumsViewHolder>(
    COMPARATOR
){

    inner class FollowingAlbumsViewHolder(private val binding: ItemAlbumsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Albums.Album) {
            binding.album = album
            binding.like = LikeStatus(album.liked,album.likeCount)
            binding.setOnClickItem{ view ->
                itemOnClickListener(ItemClickArgs(binding, view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsViewHolder {
        return FollowingAlbumsViewHolder(ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FollowingAlbumsViewHolder, position: Int) {
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