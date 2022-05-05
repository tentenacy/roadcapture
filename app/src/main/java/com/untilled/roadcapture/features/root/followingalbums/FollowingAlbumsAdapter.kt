package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum
import com.untilled.roadcapture.databinding.*
import com.untilled.roadcapture.features.common.PageLoadStateAdapter
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.albums.dto.LikeStatus
import com.untilled.roadcapture.features.root.followingalbums.dto.FollowingAlbumsAdapterArgs
import com.untilled.roadcapture.features.root.followingalbums.dto.FollowingFiltersViewHolderArgs

sealed class FollowingAlbumPagingItem(val type: FollowingAlbumPagingType) {
    data class Data(val value: Albums.Album) : FollowingAlbumPagingItem(FollowingAlbumPagingType.DATA)
    data class Header(val value: PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>) :
        FollowingAlbumPagingItem(FollowingAlbumPagingType.HEADER)
    object Separator : FollowingAlbumPagingItem(FollowingAlbumPagingType.SEPARATOR)
}

enum class FollowingAlbumPagingType { HEADER, DATA, SEPARATOR }

sealed class FollowingAlbumsViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    data class FollowingFiltersViewHolder(
        val binding: ItemContainerFollowingFilterBinding,
        val followingFiltersViewHolderArgs: FollowingFiltersViewHolderArgs,
    ) : FollowingAlbumsViewHolder(binding) {

        init {
            binding.recyclerFollowingalbumsFilter.adapter = followingFiltersViewHolderArgs.adapter.withLoadStateHeaderAndFooter(
                header = PageLoadStateAdapter{followingFiltersViewHolderArgs.adapter.retry()},
                footer = PageLoadStateAdapter{followingFiltersViewHolderArgs.adapter.retry()}
            )
        }
    }

    data class FollowingAlbumViewHolder(
        val binding: ItemAlbumsBinding,
        private val itemOnClickListener: (ItemClickArgs?) -> Unit
    ) : FollowingAlbumsViewHolder(binding) {

        init {
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding, view))
            }
        }

        fun bind(album: Albums.Album) {
            binding.album = album
            binding.like = LikeStatus(album.liked, album.likeCount)
        }
    }
}

class FollowingAlbumsAdapter(
    private val followingAlbumsAdapterArgs: FollowingAlbumsAdapterArgs,
) : PagingDataAdapter<FollowingAlbumPagingItem, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsViewHolder {
        return when (viewType) {
            VIEW_TYPE_FOLLOWING_FILTER -> FollowingAlbumsViewHolder.FollowingFiltersViewHolder(
                ItemContainerFollowingFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                followingAlbumsAdapterArgs.followingFiltersViewHolderArgs,
            )
            VIEW_TYPE_FOLLOWING_ALBUM -> FollowingAlbumsViewHolder.FollowingAlbumViewHolder(
                ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                followingAlbumsAdapterArgs.itemOnClickListener,
            )
            else -> throw RuntimeException("세상에 이런 일이!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when(holder) {
                is FollowingAlbumsViewHolder.FollowingFiltersViewHolder -> {
//                    holder.bind((it as FollowingAlbumPagingItem.Header).value)
                }
                is FollowingAlbumsViewHolder.FollowingAlbumViewHolder -> {
                    holder.bind((it as FollowingAlbumPagingItem.Data).value)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if(it.type == FollowingAlbumPagingType.HEADER) VIEW_TYPE_FOLLOWING_FILTER else VIEW_TYPE_FOLLOWING_ALBUM
        } ?: kotlin.run {
            return VIEW_TYPE_FOLLOWING_ALBUM
        }
    }

    companion object {

        const val VIEW_TYPE_FOLLOWING_FILTER = 0
        const val VIEW_TYPE_FOLLOWING_ALBUM = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<FollowingAlbumPagingItem>() {
            override fun areItemsTheSame(
                oldItem: FollowingAlbumPagingItem,
                newItem: FollowingAlbumPagingItem
            ): Boolean {
                return if(oldItem.type == FollowingAlbumPagingType.DATA && newItem.type == FollowingAlbumPagingType.DATA) {
                    (oldItem as FollowingAlbumPagingItem.Data).value.albumId == (newItem as FollowingAlbumPagingItem.Data).value.albumId
                } else {
                    true
                }
            }

            override fun areContentsTheSame(
                oldItem: FollowingAlbumPagingItem,
                newItem: FollowingAlbumPagingItem
            ): Boolean {
                return if(oldItem.type == FollowingAlbumPagingType.DATA && newItem.type == FollowingAlbumPagingType.DATA) {
                    (oldItem as FollowingAlbumPagingItem.Data).value == (newItem as FollowingAlbumPagingItem.Data).value
                } else {
                    true
                }
            }
        }
    }
}