package com.untilled.roadcapture.features.root.mystudio

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Place
import com.untilled.roadcapture.databinding.ItemStudiosPlaceBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class PlacesAdapter(private val place: List<Place> = listOf()) : BaseRecyclerViewAdapter<Place, ItemStudiosPlaceBinding>(place) {
    override fun getLayoutResId(): Int = R.layout.item_studios_place

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.place = place[position]
    }
}