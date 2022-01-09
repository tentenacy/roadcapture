package com.untilled.roadcapture.features.base

import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel

interface EpoxyItemClickListener {
    fun onClick(
        model: DataBindingEpoxyModel,
        parentView: DataBindingEpoxyModel.DataBindingHolder,
        clickedView: View,
        position: Int)
}