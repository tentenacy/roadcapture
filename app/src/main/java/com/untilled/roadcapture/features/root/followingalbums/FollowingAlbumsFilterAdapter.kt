package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.databinding.ItemFollowingFilterBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class FollowingAlbumsFilterAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<FollowingsSortByAlbum.FollowingSortByAlbum, FollowingAlbumsFilterAdapter.FollowingAlbumsFilterViewHolder>(
    COMPARATOR
) {
    var index: Int? = null
    inner class FollowingAlbumsFilterViewHolder(val binding: ItemFollowingFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: FollowingsSortByAlbum.FollowingSortByAlbum, position: Int){
            binding.position = position
            binding.user = user
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsFilterViewHolder {
        return FollowingAlbumsFilterViewHolder(ItemFollowingFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FollowingAlbumsFilterViewHolder, position: Int) {
        getItem(position)?.let{
            setSelectedStatus(holder, position)
            holder.bind(it, position)
        }
    }

    private fun setSelectedStatus(
        holder: FollowingAlbumsFilterViewHolder,
        position: Int
    ) {
        if (index == null) {
            holder.binding.viewIfollowingFilterOverlay.setBackgroundResource(R.drawable.overlay_gradient_following_filter)
        } else {
            if (index == position) holder.binding.viewIfollowingFilterOverlay.setBackgroundResource(
                R.drawable.overlay_gradient_following_filter
            )
            else holder.binding.viewIfollowingFilterOverlay.setBackgroundResource(R.drawable.overlay_gradient_following_filter_selected)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<FollowingsSortByAlbum.FollowingSortByAlbum>() {
            override fun areItemsTheSame(oldItem: FollowingsSortByAlbum.FollowingSortByAlbum, newItem: FollowingsSortByAlbum.FollowingSortByAlbum): Boolean {
                return oldItem.followingSortByAlbumId == newItem.followingSortByAlbumId
            }

            override fun areContentsTheSame(oldItem: FollowingsSortByAlbum.FollowingSortByAlbum, newItem: FollowingsSortByAlbum.FollowingSortByAlbum): Boolean {
                return oldItem == newItem
            }
        }
    }
}