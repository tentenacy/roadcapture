package com.untilled.roadcapture.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import com.untilled.roadcapture.databinding.ActivityMainBinding
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import com.untilled.roadcapture.utils.currentFragment
import com.untilled.roadcapture.utils.navigateFromOriginToLogin
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import com.yalantis.ucrop.UCropFragmentCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UCropFragmentCallback {
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    @Inject
    lateinit var oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManagerSubject>

    private val isLoggedOutObserver: (Boolean) -> Unit = { isLoggedOut ->
        if(isLoggedOut) {
            navigateFromOriginToLogin(binding.root.id)
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

    private fun initData() {
        viewModel
    }

    private fun observeData() {
        viewModel.isLoggedOut.observe(this, isLoggedOutObserver)
    }

    override fun loadingProgress(showLoader: Boolean) {
        val cropFragment: CropFragment = currentFragment(binding.root.id) as CropFragment

        cropFragment.mShowLoader = showLoader
        cropFragment.invalidateOptionsMenu()
    }

    override fun onCropFinish(result: UCropFragment.UCropResult?) {
        val cropFragment: CropFragment = currentFragment(binding.root.id) as CropFragment
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

}