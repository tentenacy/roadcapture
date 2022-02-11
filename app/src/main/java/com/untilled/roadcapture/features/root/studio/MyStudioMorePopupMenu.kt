package com.untilled.roadcapture.features.root.studio

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.untilled.roadcapture.R

class MyStudioMorePopupMenu private constructor(context: Context, view: View): PopupMenu(context, view) {

    constructor(context: Context, view: View, listener: OnMenuItemClickListener): this(context, view) {
        menuInflater.inflate(R.menu.popupmenu_mystudio_more, menu)
        setOnMenuItemClickListener(listener)
    }
}