package com.untilled.roadcapture.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.features.root.albums.AlbumsFragment
import com.untilled.roadcapture.features.root.capture.AlbumCreationAskingBottomSheetDialog
import com.untilled.roadcapture.features.root.followingalbums.FollowingAlbumsFragment
import com.untilled.roadcapture.features.root.studio.MyStudioFragment
import com.untilled.roadcapture.features.signup.SignupEmailFragment
import com.untilled.roadcapture.features.signup.SignupPasswordFragment
import com.untilled.roadcapture.features.signup.SignupUsernameFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

// Milliseconds used for UI animations in Camera
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

fun View.setStatusBarTransparent(activity: Activity) = activity.run {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setPaddingWhenStatusBarTransparent(this)
}

fun Activity.setStatusBarOrigin() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
}

fun View.setPaddingWhenStatusBarTransparent(context: Context) = context.run {
    setPadding(0, this.statusBarHeight(), 0, this.navigationHeight())
}

fun View.setPaddingBottomWhenStatusBarTransparent(context: Context) = context.run {
    setPadding(0, 0, 0, this.navigationHeight())
}

fun View.setPaddingTopWhenStatusBarTransparent(context: Context) = context.run {
    setPadding(0, this.statusBarHeight(), 0, 0)
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

fun Context.getSmoothScroll(): LinearSmoothScroller {
    return object : LinearSmoothScroller(this) {
        override fun getVerticalSnapPreference(): Int {
            return SNAP_TO_START
        }
    }
}

fun RecyclerView.scrollToPositionSmooth(int: Int) {
    this.layoutManager?.startSmoothScroll(this.context.getSmoothScroll().apply {
        targetPosition = int
    })
}

fun RecyclerView.scrollToPositionFast(position: Int) {
    this.layoutManager?.scrollToPosition(position)
}

fun Fragment.initPagingLoadStateFlow(adapter: PagingDataAdapter<*, *>, recyclerView: RecyclerView) = lifecycleScope.launchWhenCreated {
    adapter.loadStateFlow
        .distinctUntilChangedBy { it.refresh }
        .filter { it.refresh is LoadState.NotLoading }
        .collect { recyclerView.scrollToPositionSmooth(0) }
}

fun EditText.isPosOutOf(x: Float, y: Float) = x < left || x > right || y < top || y > bottom

fun BottomSheetDialogFragment.expandFullHeight() {
    val bottomSheet = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet) as View
    val behavior = BottomSheetBehavior.from<View>(bottomSheet)
    val layoutParams = bottomSheet.layoutParams
    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    bottomSheet.layoutParams = layoutParams
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    behavior.skipCollapsed = true
}

fun showSnackbar(view: View, message: String){
    Snackbar.make(view,message, Snackbar.LENGTH_INDEFINITE)
        .setAction("확인"){}
        .show()
}

fun <T: ViewDataBinding> viewBind(parent: ViewGroup, layoutRes: Int): T {
    return DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        layoutRes,
        parent,
        false
    )
}