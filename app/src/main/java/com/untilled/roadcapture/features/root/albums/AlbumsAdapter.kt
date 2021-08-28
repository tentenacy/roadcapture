package com.untilled.roadcapture.features.root.albums

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.ItemHomeAlbumBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class AlbumsAdapter(private val albums: List<Album> = listOf()) : BaseRecyclerViewAdapter<Album, ItemHomeAlbumBinding>(albums) {

    private lateinit var albumsItemClickListener: AlbumsItemClickListener

    override fun getLayoutResId(): Int = R.layout.item_home_album

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.album = albums[position]

        holder.binding!!.imageviewItemHomeAlbumComment.setOnClickListener {
            albumsItemClickListener.setOnClickListeners()
        }
    }

    fun setOnClickListeners(albumsItemClickListener: AlbumsItemClickListener){
        this.albumsItemClickListener = albumsItemClickListener
    }

}
