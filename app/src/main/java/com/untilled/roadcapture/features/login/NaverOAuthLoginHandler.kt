package com.untilled.roadcapture.features.login

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.utils.instance.OAuthLoginInstances
import com.untilled.roadcapture.utils.type.SocialType

class NaverOAuthLoginHandler(private val fragment: Fragment) : OAuthLoginHandler() {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun run(success: Boolean): Unit = fragment.requireContext().run {
        if (success) {
            onSuccess()
        } else {
            onError()
        }
        onCancel()
    }

    private fun onSuccess() = fragment.requireContext().run {
        viewModel.saveOAuthToken(
            OAuthTokenArgs(
                accessToken = OAuthLoginInstances.naverOAuthLoginInstance.getAccessToken(
                    this
                ),
                refreshToken = OAuthLoginInstances.naverOAuthLoginInstance.getRefreshToken(
                    this
                ),
            )
        )

        viewModel.socialLogin(SocialType.NAVER)
    }

    private fun onError() = fragment.requireContext().run {
        Toast.makeText(
            this,
            "errorCode: ${
                OAuthLoginInstances.naverOAuthLoginInstance.getLastErrorCode(
                    this
                )
            }, errorDesc: ${
                OAuthLoginInstances.naverOAuthLoginInstance.getLastErrorDesc(this)
            }",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onCancel() {

    }
}