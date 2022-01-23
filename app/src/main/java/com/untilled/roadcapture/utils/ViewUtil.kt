package com.untilled.roadcapture.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.features.root.albums.AlbumsFragment
import com.untilled.roadcapture.features.root.capture.AlbumCreationAskingBottomSheetDialog
import com.untilled.roadcapture.features.root.followingalbums.FollowingAlbumsFragment
import com.untilled.roadcapture.features.root.studio.MyStudioFragment
import com.untilled.roadcapture.features.signup.SignupEmailFragment
import com.untilled.roadcapture.features.signup.SignupPasswordFragment
import com.untilled.roadcapture.features.signup.SignupUsernameFragment

// Milliseconds used for UI animations in Camera
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

fun ConstraintLayout.setStatusBarTransparent(activity: Activity) = activity.run {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setPaddingWhenStatusBarTransparent(this)
}

fun Activity.setStatusBarOrigin() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
}

fun ConstraintLayout.setPaddingWhenStatusBarTransparent(context: Context) = context.run {
    setPadding(0, this.statusBarHeight(), 0, this.navigationHeight())
}

fun Activity.hideKeyboard(editText: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
}

fun ImageView.setTint(colorId: Int) {
    DrawableCompat.setTint(DrawableCompat.wrap(drawable), colorId)
}

fun View.setRippleEffect() {
    isClickable = true
    isFocusable = true
    foreground = context.getDrawableFrom(android.R.attr.selectableItemBackground)
}

fun MainActivity.currentFragment(): Fragment? = supportFragmentManager.findFragmentById(binding.root.id)?.childFragmentManager?.fragments?.get(0)