package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.ItemHomeAlbumBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class AlbumsAdapter(private val albums: List<Album> = listOf()) : BaseRecyclerViewAdapter<Album, ItemHomeAlbumBinding>(albums) {

    private lateinit var albumsItemClickListener: AlbumsItemClickListener

    private var flagLike : Boolean = false

    override fun getLayoutResId(): Int = R.layout.item_home_album

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.album = albums[position]

        holder.binding!!.imageviewItemHomeAlbumComment.setOnClickListener {
            albumsItemClickListener.setOnClickListeners()
        }

        holder.binding.imageviewItemHomeAlbumLike.setOnClickListener {
            if(!flagLike){
                val animator = ValueAnimator.ofFloat(0f,0.5f).setDuration(800)
                animator.addUpdateListener {
                    holder.binding.imageviewItemHomeAlbumLike.progress = it.animatedValue as Float
                }
                animator.start()
                flagLike = true
            }
            else{
                val animator = ValueAnimator.ofFloat(0.5f,1f).setDuration(800)
                animator.addUpdateListener {
                    holder.binding.imageviewItemHomeAlbumLike.progress = it.animatedValue as Float
                }
                animator.start()
                flagLike = false
            }
        }
    }

    fun setOnClickListeners(albumsItemClickListener: AlbumsItemClickListener){
        this.albumsItemClickListener = albumsItemClickListener
    }

}
