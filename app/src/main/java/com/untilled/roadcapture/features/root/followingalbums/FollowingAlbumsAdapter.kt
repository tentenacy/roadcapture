package com.untilled.roadcapture.features.root.followingalbums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
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
import com.untilled.roadcapture.features.root.studio.MyStudioAdapter
import com.untilled.roadcapture.features.root.studio.UserAlbumItem
import com.untilled.roadcapture.features.root.studio.UserAlbumType
import com.untilled.roadcapture.features.root.studio.UserAlbumsViewHolder

sealed class FollowingAlbumItem(val type: FollowingAlbumType) {
    data class Data(val value: Albums.Album) : FollowingAlbumItem(FollowingAlbumType.DATA)
    data class Header(val value: PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>) :
        FollowingAlbumItem(FollowingAlbumType.HEADER)
    object Separator : FollowingAlbumItem(FollowingAlbumType.SEPARATOR)
}

enum class FollowingAlbumType { HEADER, DATA, SEPARATOR }

sealed class FollowingAlbumsViewHolder(
    binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    data class FollowingFiltersViewHolder(
        val binding: ItemContainerFollowingFilterBinding,
        val lifecycle: Lifecycle,
        val filterItemOnClickListener: (ItemClickArgs?) -> Unit
    ) : FollowingAlbumsViewHolder(binding) {

        val adapter =
            FollowingAlbumsFilterAdapter(filterItemOnClickListener)

        init {
            binding.recyclerFollowingalbumsFilter.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PageLoadStateAdapter{adapter.retry()},
                footer = PageLoadStateAdapter{adapter.retry()}
            )
        }

        fun bind(filters: PagingData<FollowingsSortByAlbum.FollowingSortByAlbum>) {
            adapter.submitData(lifecycle, filters)
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
    val lifecycle: Lifecycle,
    val itemOnClickListener: (ItemClickArgs?) -> Unit,
    private val filterItemOnClickListener: (ItemClickArgs?) -> Unit,
) : PagingDataAdapter<FollowingAlbumItem, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAlbumsViewHolder {
        return when (viewType) {
            VIEW_TYPE_FOLLOWING_FILTER -> FollowingAlbumsViewHolder.FollowingFiltersViewHolder(
                ItemContainerFollowingFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false), lifecycle, filterItemOnClickListener
            )
            VIEW_TYPE_FOLLOWING_ALBUM -> FollowingAlbumsViewHolder.FollowingAlbumViewHolder(
                ItemAlbumsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                itemOnClickListener
            )
            else -> throw RuntimeException("세상에 이런 일이!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when(holder) {
                is FollowingAlbumsViewHolder.FollowingFiltersViewHolder -> {
                    holder.bind((it as FollowingAlbumItem.Header).value)
                }
                is FollowingAlbumsViewHolder.FollowingAlbumViewHolder -> {
                    holder.bind((it as FollowingAlbumItem.Data).value)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if(it.type == FollowingAlbumType.HEADER) VIEW_TYPE_FOLLOWING_FILTER else VIEW_TYPE_FOLLOWING_ALBUM
        } ?: kotlin.run {
            return VIEW_TYPE_FOLLOWING_ALBUM
        }
    }

    companion object {

        const val VIEW_TYPE_FOLLOWING_FILTER = 0
        const val VIEW_TYPE_FOLLOWING_ALBUM = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<FollowingAlbumItem>() {
            override fun areItemsTheSame(
                oldItem: FollowingAlbumItem,
                newItem: FollowingAlbumItem
            ): Boolean {
                return if(oldItem.type == FollowingAlbumType.DATA && newItem.type == FollowingAlbumType.DATA) {
                    (oldItem as FollowingAlbumItem.Data).value.albumId == (newItem as FollowingAlbumItem.Data).value.albumId
                } else {
                    false
                }
            }

            override fun areContentsTheSame(
                oldItem: FollowingAlbumItem,
                newItem: FollowingAlbumItem
            ): Boolean {
                return if(oldItem.type == FollowingAlbumType.DATA && newItem.type == FollowingAlbumType.DATA) {
                    (oldItem as FollowingAlbumItem.Data).value == (newItem as FollowingAlbumItem.Data).value
                } else {
                    false
                }
            }
        }
    }
}