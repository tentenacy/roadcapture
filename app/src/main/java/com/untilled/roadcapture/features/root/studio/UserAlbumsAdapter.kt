package com.untilled.roadcapture.features.root.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import javax.inject.Inject

class UserAlbumsAdapter @Inject constructor(): PagingDataAdapter<UserAlbums.UserAlbum, UserAlbumsAdapter.UserAlbumViewHolder>(
    COMPARATOR
) {

    class UserAlbumViewHolder(private val binding: ItemAlbumsStudioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: UserAlbums.UserAlbum) {
            binding.album = album
        }

        companion object {
            fun create(parent: ViewGroup): UserAlbumViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_albums_studio, parent, false)

                val binding = ItemAlbumsStudioBinding.bind(view)

                return UserAlbumViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAlbumViewHolder {
        return UserAlbumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UserAlbums.UserAlbum>() {
            override fun areItemsTheSame(oldItem: UserAlbums.UserAlbum, newItem: UserAlbums.UserAlbum): Boolean {
                return oldItem.userAlbumsId == newItem.userAlbumsId
            }

            override fun areContentsTheSame(oldItem: UserAlbums.UserAlbum, newItem: UserAlbums.UserAlbum): Boolean {
                return oldItem == newItem
            }
        }
    }


}