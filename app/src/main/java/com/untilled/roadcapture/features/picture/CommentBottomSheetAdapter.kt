package com.untilled.roadcapture.features.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.PictureComments
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.root.albums.dto.ItemClickArgs
import javax.inject.Inject

class CommentBottomSheetAdapter @Inject constructor() :
    PagingDataAdapter<PictureComments.PictureComment, CommentBottomSheetAdapter.CommentBottomSheetViewHolder>(
        COMPARATOR
    ) {
    private lateinit var itemClickListener: (ItemClickArgs?) -> Unit

    fun setOnClickListener(itemClickListener: (ItemClickArgs?) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    class CommentBottomSheetViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comments: PictureComments.PictureComment, itemClickListener: (ItemClickArgs?) -> Unit) {
            //TODO 회의 후 수정
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

        companion object {
            fun create(parent: ViewGroup): CommentBottomSheetViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment, parent, false)

                val binding = ItemCommentBinding.bind(view)

                return CommentBottomSheetViewHolder(
                    binding
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentBottomSheetViewHolder {
        return CommentBottomSheetViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CommentBottomSheetViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, itemClickListener)
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