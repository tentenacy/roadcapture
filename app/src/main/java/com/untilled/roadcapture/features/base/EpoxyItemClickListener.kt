package com.untilled.roadcapture.features.base

import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.untilled.roadcapture.HomeAlbumBindingModel_

interface EpoxyItemClickListener {
    fun onClick(
        model: DataBindingEpoxyModel,
        parentView: DataBindingEpoxyModel.DataBindingHolder,
        clickedView: View,
        position: Int)
}