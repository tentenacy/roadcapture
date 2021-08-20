package com.untilled.roadcapture.features.root.mystudio

import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Area
import com.untilled.roadcapture.data.entity.Studio
import com.untilled.roadcapture.databinding.ItemStudiosAreaBinding
import com.untilled.roadcapture.features.base.BaseRecyclerViewAdapter

class AreasAdapter(private val area: List<Area> = listOf()) : BaseRecyclerViewAdapter<Area, ItemStudiosAreaBinding>(area) {
    override fun getLayoutResId(): Int = R.layout.item_studios_area

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding?.area = area[position]
    }
}