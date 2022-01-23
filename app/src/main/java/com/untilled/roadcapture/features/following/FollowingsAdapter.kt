package com.untilled.roadcapture.features.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.databinding.ItemPictureSliderThumbnailBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import javax.inject.Inject

class FollowingsAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<Followings.Following,FollowingsAdapter.FollowingsViewHolder>(
    COMPARATOR
) {
    inner class FollowingsViewHolder(private val binding: ItemFollowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Followings.Following, itemOnClickListener: (ItemClickArgs?) -> Unit){
            binding.user = user
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding,view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingsViewHolder {
        return FollowingsViewHolder(ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FollowingsViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it, itemOnClickListener)
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
