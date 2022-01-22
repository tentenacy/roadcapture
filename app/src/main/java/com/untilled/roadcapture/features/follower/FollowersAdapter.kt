package com.untilled.roadcapture.features.follower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.ItemFollowBinding
import javax.inject.Inject

class FollowersAdapter @Inject constructor(): PagingDataAdapter<Followers.Follower, FollowersAdapter.FollowersViewHolder>(
    COMPARATOR
) {

    class FollowersViewHolder(private val binding: ItemFollowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: Followers.Follower){
            binding.user = Followings.Following(
                followingId = user.followerId,
                profileImageUrl = user.profileImageUrl,
                username = user.username,
                introduction = user.introduction
            )
        }

        companion object{
            fun create(parent: ViewGroup): FollowersViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_follow,parent,false)

                val binding = ItemFollowBinding.bind(view)

                return FollowersViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        return FollowersViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Followers.Follower>() {
            override fun areItemsTheSame(oldItem: Followers.Follower, newItem: Followers.Follower): Boolean {
                return oldItem.followerId == newItem.followerId
            }

            override fun areContentsTheSame(oldItem: Followers.Follower, newItem: Followers.Follower): Boolean {
                return oldItem == newItem
            }
        }
    }
}
