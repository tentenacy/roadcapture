package com.untilled.roadcapture.features.root.albums.dto

import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel

data class EpoxyItemArgs(
    val model: DataBindingEpoxyModel,
    val parentView: DataBindingEpoxyModel.DataBindingHolder,
    val clickedView: View,
    val position: Int,
)
