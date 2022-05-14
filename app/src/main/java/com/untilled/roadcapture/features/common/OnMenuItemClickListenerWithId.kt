package com.untilled.roadcapture.features.common

import android.view.MenuItem
import android.widget.PopupMenu

class OnMenuItemClickListenerWithId(private val id: Long, private val listener: (id: Long, item: MenuItem?) -> Boolean): PopupMenu.OnMenuItemClickListener {

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return listener(id, item)
    }
}