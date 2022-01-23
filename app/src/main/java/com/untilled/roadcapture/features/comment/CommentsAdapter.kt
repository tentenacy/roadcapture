package com.untilled.roadcapture.features.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class CommentsAdapter(private val itemClickListener: (ItemClickArgs?) -> Unit): PagingDataAdapter<AlbumComments.AlbumComment, CommentsAdapter.CommentViewHolder>(
    COMPARATOR
) {
    inner class CommentViewHolder(private val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(comments: AlbumComments.AlbumComment, itemClickListener: (ItemClickArgs?) -> Unit) {
            binding.comments = comments
            binding.setOnClickItem{ view ->
                itemClickListener(ItemClickArgs(binding,view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemClickListener)
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