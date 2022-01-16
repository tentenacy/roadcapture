package com.untilled.roadcapture.utils

import androidx.fragment.app.Fragment
import com.untilled.roadcapture.application.MainActivity

fun Fragment.mainActivity() = (requireActivity() as MainActivity)