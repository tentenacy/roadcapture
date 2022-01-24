package com.untilled.roadcapture.features.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class CommentBottomSheetAdapter(private val itemClickListener: (ItemClickArgs?) -> Unit) :
    PagingDataAdapter<PictureComments.PictureComment, CommentBottomSheetAdapter.CommentBottomSheetViewHolder>(
        COMPARATOR
    ) {
    inner class CommentBottomSheetViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comments: PictureComments.PictureComment) {
            binding.comments = AlbumComments.AlbumComment(
                id = comments.id,
                albumCommentsId = comments.albumCommentsId,
                pictureId = comments.pictureId,
                createdAt = comments.createdAt,
                lastModifiedAt = comments.lastModifiedAt,
                content = comments.content,
                user = comments.user
            )
            binding.setOnClickItem { view ->
                itemClickListener(ItemClickArgs(binding, view))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentBottomSheetViewHolder {
        return CommentBottomSheetViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CommentBottomSheetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PictureComments.PictureComment>() {
            override fun areItemsTheSame(oldItem: PictureComments.PictureComment, newItem: PictureComments.PictureComment): Boolean {
                return oldItem.albumCommentsId == newItem.albumCommentsId
            }

            override fun areContentsTheSame(oldItem: PictureComments.PictureComment, newItem: PictureComments.PictureComment): Boolean {
                return oldItem == newItem
            }
        }
    }
}