package com.untilled.roadcapture.application

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import com.untilled.roadcapture.databinding.ActivityMainBinding
import com.untilled.roadcapture.features.common.LoadingDialog
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.untilled.roadcapture.utils.currentFragment
import com.untilled.roadcapture.utils.isPosOutOf
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import com.yalantis.ucrop.UCropFragmentCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UCropFragmentCallback {

    lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var loadingDialog: LoadingDialog

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    fun showLoading(tag: String) {
        if(!loadingDialog.isAdded) {
            loadingDialog.show(supportFragmentManager, tag)
        }
    }

    fun dismissLoading() {
        if(loadingDialog.isAdded) {
            loadingDialog.dismissAllowingStateLoss()
        }
    }

    private val logoutObserver: (View) -> Unit = { bindingRoot ->
        Navigation.findNavController(bindingRoot).apply {
            navigate(R.id.action_global_loginFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RoadCapture)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        observeData()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        if (view != null
            && (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_MOVE)
            && view is EditText
            && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            val scrcoords = intArrayOf(0, 0)
            view.getLocationOnScreen(scrcoords)

            val x = ev.rawX + view.left - scrcoords[0]
            val y = ev.rawY + view.top - scrcoords[1]

            if (view.isPosOutOf(x, y)) {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun loadingProgress(showLoader: Boolean) {
        val cropFragment: CropFragment = currentFragment() as CropFragment

        cropFragment.mShowLoader = showLoader
        cropFragment.invalidateOptionsMenu()
    }

    override fun onCropFinish(result: UCropFragment.UCropResult?) {
        val cropFragment: CropFragment = currentFragment() as CropFragment
        when (result?.mResultCode) {
            RESULT_OK -> {
                cropFragment.handleCropResult(result.mResultData)
            }
            UCrop.RESULT_ERROR -> {
                cropFragment.handleCropError(result.mResultData)
            }
        }
        cropFragment.removeFragmentFromScreen()
    }

    private fun initData() {
        viewModel
    }

    private fun observeData() {
        viewModel.logout.observe(this, logoutObserver)
    }
}