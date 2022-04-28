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

class FollowingAlbumsFilterViewHolder(val binding: ItemFollowingFilterBinding, private val itemOnClickListener: (ItemClickArgs?) -> Unit): RecyclerView.ViewHolder(binding.root){

    init {
        binding.setOnClickItem { view ->
            itemOnClickListener(ItemClickArgs(binding,view))
            binding.viewIfollowingFilterOverlay.setBackgroundResource(R.drawable.overlay_gradient_following_filter_selected)
        }
    }

    fun bind(user: FollowingsSortByAlbum.FollowingSortByAlbum){
        //TODO: 필터 선택 시 효과 적용 (뷰모델 고려)
        binding.user = user
        binding.viewIfollowingFilterOverlay.setBackgroundResource(R.drawable.overlay_gradient_following_filter)
    }
}

class FollowingAlbumsFilterAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<FollowingsSortByAlbum.FollowingSortByAlbum, FollowingAlbumsFilterViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsFilterViewHolder {
        return FollowingAlbumsFilterViewHolder(ItemFollowingFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false), itemOnClickListener)
    }

    override fun onBindViewHolder(holder: FollowingAlbumsFilterViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bind(it)
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