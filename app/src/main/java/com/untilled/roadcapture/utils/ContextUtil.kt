package com.untilled.roadcapture.utils

import androidx.fragment.app.Fragment
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.features.picture.PictureViewerFragment
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.capture.CaptureFragment
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.untilled.roadcapture.features.root.capture.PictureEditorFragment
import com.untilled.roadcapture.features.root.capture.PlaceSearchFragment
import com.untilled.roadcapture.features.signup.SignupFragment

fun Fragment.mainActivity() = (requireActivity() as MainActivity)

fun Fragment.rootFrom1Depth(): RootFragment = (parentFragment as RootFragment)

fun Fragment.rootFrom3Depth(): RootFragment = (parentFragment?.parentFragment?.parentFragment as RootFragment)

fun Fragment.signupFrom2Depth(): SignupFragment = (parentFragment?.parentFragment as SignupFragment)

fun Fragment.pictureViewerFrom2Depth(): PictureViewerFragment = (parentFragment?.parentFragment as PictureViewerFragment)