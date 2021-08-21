package com.untilled.roadcapture.features.root.mystudio

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.ItemStudiosAlbumBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class StudiosAdapter(private val album: List<Album> = listOf()) : BaseRecyclerViewAdapter<Album, ItemStudiosAlbumBinding>(album) {
    override fun getLayoutResId(): Int = R.layout.item_studios_album

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.studio = album[position]
    }

}