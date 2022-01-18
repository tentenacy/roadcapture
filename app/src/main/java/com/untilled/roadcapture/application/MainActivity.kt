package com.untilled.roadcapture.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import com.untilled.roadcapture.databinding.ActivityMainBinding
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.untilled.roadcapture.utils.manager.OAuthLoginManager
import com.untilled.roadcapture.utils.type.SocialType
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
    lateinit var naverLoginManager: OAuthLogin

    @Inject
    lateinit var oauthLoginManagerMap: Map<String, @JvmSuppressWildcards OAuthLoginManager>

    private val originToLoginFragmentObserver: (ConstraintLayout) -> Unit = { bindingRoot ->
        if (bindingRoot.isNotEmpty()) {
            Navigation.findNavController(bindingRoot).apply {
                navigate(R.id.action_global_loginFragment)
                Navigation.findNavController(bindingRoot).popBackStack()
            }
        }
    }

    private val logoutObserver: (SocialType) -> Unit = {
        oauthLoginManagerMap[it.name]?.logout()
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
        naverLoginManager.init(
            this,
            BuildConfig.SOCIAL_NAVER_CLIENT_ID,
            BuildConfig.SOCIAL_NAVER_CLIENT_SECRET,
            BuildConfig.SOCIAL_NAVER_CLIENT_NAME
        )
    }

    private fun observeData() {
        viewModel.originToLoginFragment.observe(this, originToLoginFragmentObserver)
        viewModel.logout.observe(this, logoutObserver)
    }

    override fun loadingProgress(showLoader: Boolean) {
        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(binding.root.id)
        val cropFragment: CropFragment =
            navHostFragment?.childFragmentManager!!.fragments[0] as CropFragment

        cropFragment.mShowLoader = showLoader
        cropFragment.invalidateOptionsMenu()
    }

    override fun onCropFinish(result: UCropFragment.UCropResult?) {
        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(binding.root.id)
        val cropFragment: CropFragment =
            navHostFragment?.childFragmentManager!!.fragments[0] as CropFragment
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