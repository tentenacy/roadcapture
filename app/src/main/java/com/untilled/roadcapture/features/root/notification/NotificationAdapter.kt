package com.untilled.roadcapture.features.root.notification

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.ItemNotificationBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class NotificationAdapter(private  val notification: List<Album> = listOf()) : BaseRecyclerViewAdapter<Album, ItemNotificationBinding>(notification) {
    override fun getLayoutResId(): Int = R.layout.item_notification

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.notification = notification[position]
    }

}