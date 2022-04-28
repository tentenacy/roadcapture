package com.untilled.roadcapture.features.root.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.databinding.ItemLabelAlbumsStudioBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import java.lang.RuntimeException


sealed class UserAlbumItem(val type: UserAlbumType) {
    data class Data(val value: UserAlbums.UserAlbum): UserAlbumItem(UserAlbumType.DATA)
    object Header: UserAlbumItem(UserAlbumType.HEADER)
    object Separator: UserAlbumItem(UserAlbumType.SEPARATOR)
}

enum class UserAlbumType { HEADER, DATA, SEPARATOR }

sealed class UserAlbumsViewHolder(
    binding: ViewDataBinding,
): RecyclerView.ViewHolder(binding.root) {

    data class LabelViewHolder(val binding: ItemLabelAlbumsStudioBinding): UserAlbumsViewHolder(binding) {

    }

    data class UserAlbumViewHolder(val binding: ItemAlbumsStudioBinding, private val itemOnClickListener: (ItemClickArgs?) -> Unit): UserAlbumsViewHolder(binding) {

        init {
            binding.setOnClickItem { view ->
                itemOnClickListener(ItemClickArgs(binding, view))
            }
        }

        fun bind(album: UserAlbums.UserAlbum) {
            binding.album = album
        }
    }
}

class MyStudioAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<UserAlbumItem, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.let {
            return if(it.type == UserAlbumType.HEADER) VIEW_TYPE_LABEL else VIEW_TYPE_ALBUM
        } ?: kotlin.run {
            return VIEW_TYPE_ALBUM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            VIEW_TYPE_LABEL -> UserAlbumsViewHolder.LabelViewHolder(ItemLabelAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            VIEW_TYPE_ALBUM -> UserAlbumsViewHolder.UserAlbumViewHolder(ItemAlbumsStudioBinding.inflate(LayoutInflater.from(parent.context),parent,false), itemOnClickListener)
            else -> throw RuntimeException("세상에 이런 일이!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == VIEW_TYPE_LABEL) return
        getItem(position)?.let {
            when(holder) {
                is UserAlbumsViewHolder.UserAlbumViewHolder -> {
                    holder.bind((it as UserAlbumItem.Data).value)
                }
            }
        }
    }

    companion object {

        const val VIEW_TYPE_LABEL = 0
        const val VIEW_TYPE_ALBUM = 1

        private val COMPARATOR = object : DiffUtil.ItemCallback<UserAlbumItem>() {
            override fun areItemsTheSame(oldItem: UserAlbumItem, newItem: UserAlbumItem): Boolean {
                return if(oldItem.type == UserAlbumType.DATA && newItem.type == UserAlbumType.DATA) {
                    (oldItem as UserAlbumItem.Data).value.userAlbumId == (newItem as UserAlbumItem.Data).value.userAlbumId
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: UserAlbumItem, newItem: UserAlbumItem): Boolean {
                return if(oldItem.type == UserAlbumType.DATA && newItem.type == UserAlbumType.DATA) {
                    (oldItem as UserAlbumItem.Data).value == (newItem as UserAlbumItem.Data).value
                } else {
                    false
                }
            }
        }
    }
}