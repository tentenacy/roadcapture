package com.untilled.roadcapture.features.picture

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureResponse
import com.untilled.roadcapture.databinding.ItemAlbumsStudioBinding
import com.untilled.roadcapture.databinding.ItemPictureSliderContentBinding
import com.untilled.roadcapture.databinding.ItemPictureSliderThumbnailBinding
import javax.inject.Inject

class PictureViewerAdapter:
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var content = emptyList<PictureResponse>()
    var item: AlbumResponse? = null
        set(value) {
            content = value?.pictures!!
            field = value
        }

    inner class PictureContentViewHolder(private val binding: ItemPictureSliderContentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(content: PictureResponse){
            binding.picture = content
        }
    }

    inner class PictureThumbnailViewHolder(private val binding: ItemPictureSliderThumbnailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(thumbnail: AlbumResponse?) {
            binding.album = thumbnail
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            0 -> PictureThumbnailViewHolder(ItemPictureSliderThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> PictureContentViewHolder(ItemPictureSliderContentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            0 -> (holder as PictureThumbnailViewHolder).bind(item)
            else -> (holder as PictureContentViewHolder).bind(content[position - 1])
        }
    }

    override fun getItemCount(): Int = content.size + 1

}