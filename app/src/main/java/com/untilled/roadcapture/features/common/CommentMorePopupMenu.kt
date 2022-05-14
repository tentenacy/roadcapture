package com.untilled.roadcapture.features.common

import android.content.Context
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import com.untilled.roadcapture.R
import com.untilled.roadcapture.features.comment.CommentViewModel
import com.untilled.roadcapture.features.common.dto.ItemClickArgs

class CommentMorePopupMenu private constructor(context: Context, view: View): PopupMenu(context, view) {

    constructor(context: Context, view: View, listener: OnMenuItemClickListener): this(context, view) {
        menuInflater.inflate(R.menu.popupmenu_comment_more, menu)
        setOnMenuItemClickListener(listener)
    }
}