package com.untilled.roadcapture.features.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.root.albums.AlbumsAdapter
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import javax.inject.Inject

class CommentsAdapter @Inject constructor(): PagingDataAdapter<AlbumComments.AlbumComment, CommentsAdapter.CommentViewHolder>(
    COMPARATOR
) {
    private lateinit var itemClickListener: (ItemClickArgs?) -> Unit

    fun setOnClickListener(itemClickListener: (ItemClickArgs?) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    class CommentViewHolder(private val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(comments: AlbumComments.AlbumComment, itemClickListener: (ItemClickArgs?) -> Unit) {
            binding.comments = comments
            binding.setOnClickItem{ view ->
                itemClickListener(ItemClickArgs(binding,view))
            }
        }

        companion object {
            fun create(parent: ViewGroup): CommentViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment, parent, false)

                val binding = ItemCommentBinding.bind(view)

                return CommentViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it,itemClickListener)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<AlbumComments.AlbumComment>() {
            override fun areItemsTheSame(oldItem:AlbumComments.AlbumComment, newItem: AlbumComments.AlbumComment): Boolean {
                return oldItem.albumCommentsId == newItem.albumCommentsId
            }

            override fun areContentsTheSame(oldItem: AlbumComments.AlbumComment, newItem: AlbumComments.AlbumComment): Boolean {
                return oldItem == newItem
            }
        }
    }


}