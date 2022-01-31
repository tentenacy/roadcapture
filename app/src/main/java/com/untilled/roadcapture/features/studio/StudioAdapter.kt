package com.untilled.roadcapture.features.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.databinding.ItemLabelAlbumsStudioBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.studio.MyStudioAdapter

class StudioAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<UserAlbums.UserAlbum, RecyclerView.ViewHolder>(
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

    inner class StudioLabelViewHolder(private val binding: ItemLabelAlbumsStudioBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){
            //binding.albumCount = albumCount
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> StudioLabelViewHolder(ItemLabelAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> StudioAlbumsViewHolder(ItemAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> (holder as StudioLabelViewHolder).bind()
            else -> getItem(position - 1)?.let{
                (holder as StudioAlbumsViewHolder).bind(it)
            }
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