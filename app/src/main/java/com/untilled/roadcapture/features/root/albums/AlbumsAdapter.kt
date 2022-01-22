package com.untilled.roadcapture.features.root.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.albums.dto.LikeStatus
import javax.inject.Inject

class AlbumsAdapter @Inject constructor(): PagingDataAdapter<Albums.Album, AlbumsAdapter.AlbumViewHolder>(
    COMPARATOR
) {
    private lateinit var itemClickListener: (ItemClickArgs?) -> Unit

    fun setOnClickListener(itemClickListener: (ItemClickArgs?) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    class AlbumViewHolder(private val binding: ItemAlbumsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Albums.Album, itemClickListener: (ItemClickArgs?) -> Unit) {
            binding.album = album
            binding.like = LikeStatus(album.liked,album.likeCount)
            binding.setOnClickItem{ view ->
                itemClickListener(ItemClickArgs(binding,view))
            }
        }

        companion object {
            fun create(parent: ViewGroup): AlbumViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_albums, parent, false)

                val binding = ItemAlbumsBinding.bind(view)

                return AlbumViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,itemClickListener)
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