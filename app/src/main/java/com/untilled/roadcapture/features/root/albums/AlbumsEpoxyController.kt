package com.untilled.roadcapture.features.root.albums

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.untilled.roadcapture.HomeAlbumBindingModel_
import com.untilled.roadcapture.data.dto.album.Albums
import com.untilled.roadcapture.features.base.EpoxyItemClickListener

class AlbumsEpoxyController : PagingDataEpoxyController<Albums>() {

    private lateinit var epoxyItemClickListener: EpoxyItemClickListener

    override fun buildItemModel(currentPosition: Int, item: Albums?): EpoxyModel<*> {
        return HomeAlbumBindingModel_()
            .id("albums${currentPosition}")
            .albums(item)
            .onClickItem { model, parentView, clickedView, position ->
                epoxyItemClickListener.onClick(model,parentView,clickedView,position)
            }
    }

    fun setOnClickListener(epoxyItemClickListenerListener: EpoxyItemClickListener){
        this.epoxyItemClickListener = epoxyItemClickListenerListener
    }
}