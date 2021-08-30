package com.untilled.roadcapture.features.root.mystudio.follow

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.User
import com.untilled.roadcapture.databinding.ItemFollowBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class FollowAdapter(private val user: List<User> = listOf()) : BaseRecyclerViewAdapter<User, ItemFollowBinding>(user) {
    override fun getLayoutResId(): Int = R.layout.item_follow

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.user = user[position]
    }

}