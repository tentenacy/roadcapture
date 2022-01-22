package com.untilled.roadcapture.features.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import javax.inject.Inject

class FollowingsAdapter @Inject constructor(): PagingDataAdapter<Followings.Following,FollowingsAdapter.FollowingsViewHolder>(
    COMPARATOR
) {

    class FollowingsViewHolder(private val binding: ItemFollowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user: Followings.Following){
            binding.user = user
        }

        companion object{
            fun create(parent: ViewGroup): FollowingsViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_follow,parent,false)

                val binding = ItemFollowBinding.bind(view)

                return FollowingsViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingsViewHolder {
        return FollowingsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FollowingsViewHolder, position: Int) {
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
