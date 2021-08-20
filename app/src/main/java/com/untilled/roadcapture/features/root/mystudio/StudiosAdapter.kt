package com.untilled.roadcapture.features.root.mystudio

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Area
import com.untilled.roadcapture.data.entity.Studio
import com.untilled.roadcapture.databinding.ItemStudiosAlbumBinding
import com.untilled.roadcapture.databinding.ItemStudiosAreaBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class StudiosAdapter(private val studio: List<Studio> = listOf()) : BaseRecyclerViewAdapter<Studio, ItemStudiosAlbumBinding>(studio) {
    override fun getLayoutResId(): Int = R.layout.item_studios_album

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.studio = studio[position]
    }
}