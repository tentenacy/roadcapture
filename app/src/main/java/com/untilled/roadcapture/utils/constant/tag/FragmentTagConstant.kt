package com.untilled.roadcapture.utils.constant.tag

import com.untilled.roadcapture.R

object FragmentTagConstant {
    const val PICTURE_VIEWER_SLIDER = "pictureViewerSliderFragment"
    const val PICTURE_VIEWER_MAP = "pictureViewerMapFragment"
    val ROOT = mapOf<Int, String>(
        R.id.menu_albums to "albumsFragment",
        R.id.menu_search to "searchRootFragment",
        R.id.menu_followingalbums to "followingAlbumsFragment",
        R.id.menu_mystudio to "myStudioFragment",
    )
}