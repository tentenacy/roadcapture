package com.untilled.roadcapture.features.root.comment

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.untilled.roadcapture.CommentBindingModel_
import com.untilled.roadcapture.data.dto.comment.Comments
import com.untilled.roadcapture.features.base.EpoxyItemClickListener

class CommentsEpoxyController : PagingDataEpoxyController<Comments>(){

    private lateinit var epoxyItemClickListener: EpoxyItemClickListener

    override fun buildItemModel(currentPosition: Int, item: Comments?): EpoxyModel<*> {

        return CommentBindingModel_()
            .id("comments${currentPosition}")
            .comments(item)
            .onClickItem { model, parentView, clickedView, position ->
                epoxyItemClickListener.onClick(model,parentView,clickedView,position)
            }

    }

    fun setOnClickListener(epoxyItemClickListenerListener: EpoxyItemClickListener){
        this.epoxyItemClickListener = epoxyItemClickListenerListener
    }
}