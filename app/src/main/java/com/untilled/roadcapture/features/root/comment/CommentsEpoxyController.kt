package com.untilled.roadcapture.features.root.comment

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.untilled.roadcapture.CommentBindingModel_
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.features.base.EpoxyItemClickListener
import com.untilled.roadcapture.features.root.albums.dto.EpoxyItemArgs

class CommentsEpoxyController : PagingDataEpoxyController<Comments>(){

    private lateinit var epoxyItemClickListener: (EpoxyItemArgs) -> Unit

    override fun buildItemModel(currentPosition: Int, item: Comments?): EpoxyModel<*> {

        return CommentBindingModel_()
            .id("comments${currentPosition}")
            .comments(item)
            .onClickItem { model, parentView, clickedView, position ->
                epoxyItemClickListener(EpoxyItemArgs(model,parentView,clickedView,position))
            }

    }

    fun setOnClickListener(epoxyItemClickListenerListener: (EpoxyItemArgs) -> Unit){
        this.epoxyItemClickListener = epoxyItemClickListenerListener
    }
}