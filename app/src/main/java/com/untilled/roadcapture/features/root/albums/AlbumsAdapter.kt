package com.untilled.roadcapture.features.root.albums

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.ItemHomeAlbumBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class AlbumsAdapter(private val albums: List<Album> = listOf()) : BaseRecyclerViewAdapter<Album, ItemHomeAlbumBinding>(albums) {
    override fun getLayoutResId(): Int = R.layout.item_home_album

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.album = albums[position]
    }
}
