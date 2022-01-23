package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.databinding.ItemFollowingFilterBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class FollowingAlbumsFilterAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<Followings.Following, FollowingAlbumsFilterAdapter.FollowingAlbumsFilterViewHolder>(
    COMPARATOR
) {

    inner class FollowingAlbumsFilterViewHolder(private val binding: ItemFollowingFilterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: Followings.Following){
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
            holder.bind(it)
        }
    }
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Followings.Following>() {
            override fun areItemsTheSame(oldItem: Followings.Following, newItem: Followings.Following): Boolean {
                return oldItem.followingId == newItem.followingId
            }

            override fun areContentsTheSame(oldItem: Followings.Following, newItem: Followings.Following): Boolean {
                return oldItem == newItem
            }
        }
    }
}