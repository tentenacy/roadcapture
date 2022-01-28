package com.untilled.roadcapture.features.root.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest
import com.untilled.roadcapture.data.datasource.api.ext.dto.poi.Poi
import com.untilled.roadcapture.databinding.ItemPlaceSearchBinding

class PlaceSearchAdapter(private val itemOnClickListener: (Poi?) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var poiList: List<Poi> = emptyList()

    fun clear() {
        poiList = emptyList()
    }

    inner class PlaceSearchViewHolder(private val binding: ItemPlaceSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poi: Poi) {
            binding.poi = poi
            binding.setOnClickItem {
                itemOnClickListener(binding.poi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceSearchViewHolder(ItemPlaceSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaceSearchViewHolder).bind(poiList[position])
    }

    override fun getItemCount(): Int = poiList.size
}