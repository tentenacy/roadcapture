package com.untilled.roadcapture.features.picture

import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.PagerSnapHelper

import androidx.annotation.IntDef


class PictureSnapPagerScrollListener(// Properties
    private val snapHelper: PagerSnapHelper,
    @param:Type private val type: Int,
    private val notifyOnInit: Boolean,
    private val listener: OnChangeListener
) :
    RecyclerView.OnScrollListener() {
    @IntDef(ON_SCROLL, ON_SETTLED)
    annotation class Type
    interface OnChangeListener {
        fun onSnapped(position: Int)
    }

    private var snapPosition: Int

    // Methods
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (type == ON_SCROLL || !hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (type == ON_SETTLED && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = snapHelper.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if (snapPosition != newSnapPosition) {
            if (notifyOnInit && !hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            } else if (hasItemPosition()) {
                listener.onSnapped(newSnapPosition)
            }
            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition != RecyclerView.NO_POSITION
    }

    companion object {
        // Constants
        const val ON_SCROLL = 0
        const val ON_SETTLED = 1
    }

    // Constructor
    init {
        snapPosition = RecyclerView.NO_POSITION
    }
}