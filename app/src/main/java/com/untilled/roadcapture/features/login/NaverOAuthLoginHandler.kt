package com.untilled.roadcapture.features.login

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.utils.instance.OAuthLoginInstances
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.AndroidEntryPoint

class NaverOAuthLoginHandler(private val fragment: Fragment) : OAuthLoginHandler() {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun run(success: Boolean): Unit = fragment.requireContext().run {
        if (success) {
            viewModel.saveOAuthToken(
                SocialType.NAVER, OAuthTokenArgs(
                    accessToken = OAuthLoginInstances.naverOAuthLoginInstance.getAccessToken(
                        this
                    ),
                    expiresIn = OAuthLoginInstances.naverOAuthLoginInstance.getExpiresAt(this)
                        .toInt(),
                    refreshToken = OAuthLoginInstances.naverOAuthLoginInstance.getRefreshToken(
                        this
                    ),
                    tokenType = OAuthLoginInstances.naverOAuthLoginInstance.getTokenType(this),
                )
            )

            viewModel.socialLogin(SocialType.NAVER)
        } else {
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
    }
}