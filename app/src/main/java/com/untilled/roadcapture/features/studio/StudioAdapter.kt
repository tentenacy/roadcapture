package com.untilled.roadcapture.features.studio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.UserAlbums
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.databinding.ItemLabelAlbumsStudioBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs
import com.untilled.roadcapture.features.root.studio.MyStudioAdapter
import com.untilled.roadcapture.features.root.studio.UserAlbumItem
import com.untilled.roadcapture.features.root.studio.UserAlbumType
import com.untilled.roadcapture.features.root.studio.UserAlbumsViewHolder
import java.lang.RuntimeException

class StudioAdapter(private val itemOnClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<UserAlbumItem, RecyclerView.ViewHolder>(
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