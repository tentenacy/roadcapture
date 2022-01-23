package com.untilled.roadcapture.features.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import javax.inject.Inject

class FollowersAdapter @Inject constructor() :
    PagingDataAdapter<Followers.Follower, FollowersAdapter.FollowersViewHolder>(
        COMPARATOR
    ) {

    lateinit var itemOnClickListener: (ItemClickArgs?) -> Unit

    inner class FollowersViewHolder(private val binding: ItemFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: Followers.Follower, itemOnClickListener: (ItemClickArgs?) -> Unit) {
            binding.user = Followings.Following(
                followingId = user.followerId,
                profileImageUrl = user.profileImageUrl,
                username = user.username,
                introduction = user.introduction
            )
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding, view))
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowersAdapter.FollowersViewHolder {
        return FollowersViewHolder(
            ItemFollowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemOnClickListener)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Followers.Follower>() {
            override fun areItemsTheSame(
                oldItem: Followers.Follower,
                newItem: Followers.Follower
            ): Boolean {
                return oldItem.followerId == newItem.followerId
            }

            override fun areContentsTheSame(
                oldItem: Followers.Follower,
                newItem: Followers.Follower
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
