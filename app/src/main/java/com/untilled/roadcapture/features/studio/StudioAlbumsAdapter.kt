package com.untilled.roadcapture.features.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class StudioAlbumsAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<UserAlbums.UserAlbum, StudioAlbumsAdapter.StudioAlbumsViewHolder>(
    COMPARATOR
) {

    inner class StudioAlbumsViewHolder(private val binding: ItemAlbumsStudioBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(album: UserAlbums.UserAlbum) {
            binding.album = album
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudioAlbumsViewHolder {
        return StudioAlbumsViewHolder(ItemAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudioAlbumsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UserAlbums.UserAlbum>() {
            override fun areItemsTheSame(oldItem: UserAlbums.UserAlbum, newItem: UserAlbums.UserAlbum): Boolean {
                return oldItem.userAlbumId == newItem.userAlbumId
            }

            override fun areContentsTheSame(oldItem: UserAlbums.UserAlbum, newItem: UserAlbums.UserAlbum): Boolean {
                return oldItem == newItem
            }
        }
    }
}