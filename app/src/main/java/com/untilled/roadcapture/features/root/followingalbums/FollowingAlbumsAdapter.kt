package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.root.albums.AlbumsAdapter
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.albums.dto.LikeStatus
import javax.inject.Inject

class FollowingAlbumsAdapter @Inject constructor(): PagingDataAdapter<Albums.Album, FollowingAlbumsAdapter.FollowingAlbumsViewHolder>(
    COMPARATOR
){
    lateinit var itemOnClickListener: (ItemClickArgs?) -> Unit

    class FollowingAlbumsViewHolder(private val binding: ItemAlbumsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(album: Albums.Album, itemOnClickListener: (ItemClickArgs?) -> Unit) {
            binding.album = album
            binding.like = LikeStatus(album.liked,album.likeCount)
            binding.setOnClickItem{ view ->
                itemOnClickListener(ItemClickArgs(binding,view))
            }
        }

        companion object {
            fun create(parent: ViewGroup): FollowingAlbumsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_albums, parent, false)

                val binding = ItemAlbumsBinding.bind(view)

                return FollowingAlbumsViewHolder(
                    binding
                )
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsViewHolder {
        return FollowingAlbumsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FollowingAlbumsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,itemOnClickListener)
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