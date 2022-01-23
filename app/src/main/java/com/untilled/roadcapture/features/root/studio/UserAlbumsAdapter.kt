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
import com.untilled.roadcapture.databinding.ItemPlaceSearchBinding
import javax.inject.Inject

class UserAlbumsAdapter: PagingDataAdapter<UserAlbums.UserAlbum, UserAlbumsAdapter.UserAlbumViewHolder>(
    COMPARATOR
) {

    inner class UserAlbumViewHolder(private val binding: ItemAlbumsStudioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: UserAlbums.UserAlbum) {
            binding.album = album
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAlbumViewHolder {
        return UserAlbumViewHolder(ItemAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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