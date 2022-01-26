package com.untilled.roadcapture.features.root.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest
import com.untilled.roadcapture.databinding.ItemPlaceSearchBinding

class PlaceSearchAdapter(private val itemOnClickListener: (PlaceCreateRequest?) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var placeCreateList: List<PlaceCreateRequest> = emptyList()

    fun clear() {
        placeCreateList = emptyList()
    }

    inner class PlaceSearchViewHolder(private val binding: ItemPlaceSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceCreateRequest) {
            binding.place = place
            binding.setOnClickItem {
                itemOnClickListener(binding.place)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceSearchViewHolder(ItemPlaceSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaceSearchViewHolder).bind(placeCreateList[position])
    }

    override fun getItemCount(): Int = placeCreateList.size
}