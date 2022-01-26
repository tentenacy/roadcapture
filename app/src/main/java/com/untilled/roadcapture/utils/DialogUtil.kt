package com.untilled.roadcapture.utils

import androidx.fragment.app.Fragment
import com.untilled.roadcapture.features.common.ReportDialogFragment
import com.untilled.roadcapture.features.root.capture.AlbumCreationCancelDialogFragment
import com.untilled.roadcapture.features.root.capture.PictureDeleteDialogFragment
import com.untilled.roadcapture.features.root.capture.ThumbnailSettingDialogFragment
import com.untilled.roadcapture.features.settings.LogoutDialogFragment
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant

fun Fragment.showReportDialog(listener: () -> Unit) {
    ReportDialogFragment(listener).show(childFragmentManager, DialogTagConstant.REPORT_DIALOG)
}

fun Fragment.showCancelAlbumCreationAskingDialog(listener: () -> Unit) {
    AlbumCreationCancelDialogFragment(listener).show(childFragmentManager, DialogTagConstant.ALBUM_CREATION_CANCEL)
}

fun Fragment.showThumbnailSettingDialog() {
    ThumbnailSettingDialogFragment().show(childFragmentManager, DialogTagConstant.THUMBNAIL_SETTING)
}

fun Fragment.showLogoutConfirmationDialog(listener: () -> Unit) {
    LogoutDialogFragment(listener).show(childFragmentManager, DialogTagConstant.LOGOUT_DIALOG)
}

fun Fragment.showPictureDeleteAskingDialog(listener: () -> Unit) {
    PictureDeleteDialogFragment(listener).show(childFragmentManager, DialogTagConstant.PICTURE_DELETE_DIALOG)
}
