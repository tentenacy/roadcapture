package com.untilled.roadcapture.features.picture

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.databinding.ItemPictureSliderContentBinding
import com.untilled.roadcapture.databinding.ItemPictureSliderThumbnailBinding
import javax.inject.Inject

class PictureViewerAdapter @Inject constructor():
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var content = emptyList<PictureResponse>()
    private var thumbnail: AlbumResponse? = null

    fun setItem(item: AlbumResponse){
        thumbnail = item
        content = item.pictures!!
    }


    class PictureContentViewHolder(private val binding: ItemPictureSliderContentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(content: PictureResponse){
            binding.picture = content
        }
        companion object{
            fun create(parent: ViewGroup): PictureContentViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_picture_slider_content, parent,false)

                val binding = ItemPictureSliderContentBinding.bind(view)

                return PictureContentViewHolder(
                    binding
                )
            }
        }
    }
    class PictureThumbnailViewHolder(private val binding: ItemPictureSliderThumbnailBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(thumbnail: AlbumResponse?){
            binding.album = thumbnail
        }
        companion object{
            fun create(parent: ViewGroup): PictureThumbnailViewHolder{
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_picture_slider_thumbnail, parent,false)

                val binding = ItemPictureSliderThumbnailBinding.bind(view)

                return PictureThumbnailViewHolder(
                    binding
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> PictureThumbnailViewHolder.create(parent)
            else -> PictureContentViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> (holder as PictureThumbnailViewHolder).bind(thumbnail)
            else -> (holder as PictureContentViewHolder).bind(content[position - 1])
        }
    }

    override fun getItemCount(): Int = content.size + 1

}