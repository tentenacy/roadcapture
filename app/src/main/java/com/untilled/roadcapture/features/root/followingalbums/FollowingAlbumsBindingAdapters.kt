package com.untilled.roadcapture.features.root.followingalbums

import android.view.View
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.R

object FollowingAlbumsBindingAdapters {
    @JvmStatic
    @BindingAdapter("SelectedStats")
    fun setSelectedStatus(view: View, selected: Boolean){
        if(selected) view.setBackgroundResource(R.drawable.overlay_gradient_following_filter_selected)
        else view.setBackgroundResource(R.drawable.overlay_gradient_following_filter)
    }
}