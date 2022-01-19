package com.untilled.roadcapture.features.root.albums

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.untilled.roadcapture.AlbumsBindingModel_
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.features.root.albums.dto.EpoxyItemArgs

class AlbumsEpoxyController : PagingDataEpoxyController<AlbumResponse>() {

    private lateinit var epoxyItemClickListener: (EpoxyItemArgs) -> Unit

    override fun buildItemModel(currentPosition: Int, item: AlbumResponse?): EpoxyModel<*> {
        return AlbumsBindingModel_()
            .id("albums${currentPosition}")
            .albums(item)
            .onClickItem { model, parentView, clickedView, position ->
                epoxyItemClickListener(EpoxyItemArgs(model,parentView,clickedView,position))
            }
    }

    fun setOnClickListener(epoxyItemClickListenerListener: (EpoxyItemArgs) -> Unit) {
        this.epoxyItemClickListener = epoxyItemClickListenerListener
    }
}