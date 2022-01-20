package com.untilled.roadcapture.features.root.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import javax.inject.Inject

class AlbumsAdapter @Inject constructor(): PagingDataAdapter<Albums.Album, AlbumsAdapter.AlbumViewHolder>(
    COMPARATOR
) {

    class AlbumViewHolder(private val binding: ItemAlbumsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Albums.Album) {
            binding.album = album
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