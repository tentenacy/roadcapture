package com.untilled.roadcapture.features.root.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceRequest
import com.untilled.roadcapture.databinding.ItemAlbumsBinding
import com.untilled.roadcapture.databinding.ItemPlaceSearchBinding
import javax.inject.Inject

class PlaceSearchAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var placeList: List<PlaceRequest>
    lateinit var itemClickListener: (PlaceRequest?) -> Unit

    fun clear() {
        placeList = emptyList()
    }

    inner class PlaceSearchViewHolder(private val binding: ItemPlaceSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceRequest) {
            binding.place = place
            binding.setOnClickItem {
                itemClickListener(binding.place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceSearchViewHolder(ItemPlaceSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaceSearchViewHolder).bind(placeList[position])
    }

    override fun getItemCount(): Int = placeList.size
}