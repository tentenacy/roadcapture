package com.untilled.roadcapture.utils

import androidx.fragment.app.Fragment
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.capture.CaptureFragment
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.untilled.roadcapture.features.root.capture.PictureEditorFragment
import com.untilled.roadcapture.features.root.capture.PlaceSearchFragment
import com.untilled.roadcapture.features.signup.SignupFragment

fun Fragment.mainActivity() = (requireActivity() as MainActivity)

fun Fragment.rootFromChild(): RootFragment = (parentFragment?.parentFragment?.parentFragment as RootFragment)

fun Fragment.signupFromChild(): SignupFragment = (parentFragment?.parentFragment as SignupFragment)