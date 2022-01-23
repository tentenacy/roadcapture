package com.untilled.roadcapture.features.common.dto

import android.view.View
import androidx.databinding.ViewDataBinding

data class ItemClickArgs(
    val item: ViewDataBinding?,
    val view: View?
)
