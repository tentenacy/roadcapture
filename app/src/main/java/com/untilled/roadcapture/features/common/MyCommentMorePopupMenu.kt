package com.untilled.roadcapture.features.common

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.untilled.roadcapture.R

class MyCommentMorePopupMenu private constructor(context: Context, view: View): PopupMenu(context,view){

    constructor(context: Context, view: View, listener: OnMenuItemClickListener): this(context,view){
        menuInflater.inflate(R.menu.popupmenu_mycomment_more, menu)
        setOnMenuItemClickListener(listener)
    }
}