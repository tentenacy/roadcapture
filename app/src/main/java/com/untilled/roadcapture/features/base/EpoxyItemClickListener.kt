package com.untilled.roadcapture.features.base

import android.view.View
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.untilled.roadcapture.features.root.albums.dto.EpoxyItemArgs

interface EpoxyItemClickListener {
    fun onClick(args: EpoxyItemArgs)
}