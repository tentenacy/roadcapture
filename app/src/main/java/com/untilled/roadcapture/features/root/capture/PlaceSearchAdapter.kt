package com.untilled.roadcapture.features.root.capture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceRequest
import com.untilled.roadcapture.databinding.ItemPlaceSearchBinding
import javax.inject.Inject

class PlaceSearchAdapter @Inject constructor() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var placeList = emptyList<PlaceRequest>()
    private lateinit var itemClickListener: (PlaceRequest?) -> Unit

    fun setPlaceList(placeList: List<PlaceRequest>) {
        this.placeList = placeList
    }

    fun setOnClickListener(itemClickListener: (PlaceRequest?) -> Unit) {
        this.itemClickListener = itemClickListener
    }

    fun clear() {
        placeList = emptyList()
    }

    class PlaceSearchViewHolder(private val binding: ItemPlaceSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceRequest, itemClickListener: (PlaceRequest?) -> Unit) {
            binding.place = place
            binding.setOnClickItem { _ ->
                itemClickListener(binding.place)
            }
        }

        companion object {
            fun create(parent: ViewGroup): PlaceSearchViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_place_search, parent, false)

                val binding = ItemPlaceSearchBinding.bind(view)

                return PlaceSearchViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceSearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaceSearchViewHolder).bind(placeList[position], itemClickListener)
    }

    override fun getItemCount(): Int = placeList.size
}