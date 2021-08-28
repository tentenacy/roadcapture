package com.untilled.roadcapture.features.root.comment

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Comment
import com.untilled.roadcapture.databinding.ItemCommentBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter
import com.untilled.roadcapture.features.root.albums.AlbumsItemClickListener

class CommentAdapter(private val comment: List<Comment> = listOf()) : BaseRecyclerViewAdapter<Comment,ItemCommentBinding>(comment) {
    override fun getLayoutResId(): Int = R.layout.item_comment

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.comment = comment[position]
    }
}