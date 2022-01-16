package com.untilled.roadcapture.application

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ActivityMainBinding
import com.untilled.roadcapture.features.login.LoginViewModel
import com.untilled.roadcapture.features.root.capture.CropFragment
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import com.yalantis.ucrop.UCropFragmentCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UCropFragmentCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RoadCapture)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun loadingProgress(showLoader: Boolean) {
        val navHostFragment : Fragment? = supportFragmentManager.findFragmentById(binding.root.id)
        val cropFragment : CropFragment = navHostFragment?.childFragmentManager!!.fragments[0] as CropFragment

        cropFragment.mShowLoader = showLoader
        cropFragment.invalidateOptionsMenu()
    }

    override fun onCropFinish(result: UCropFragment.UCropResult?) {
        val navHostFragment : Fragment? = supportFragmentManager.findFragmentById(binding.root.id)
        val cropFragment : CropFragment = navHostFragment?.childFragmentManager!!.fragments[0] as CropFragment
        when(result?.mResultCode){
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