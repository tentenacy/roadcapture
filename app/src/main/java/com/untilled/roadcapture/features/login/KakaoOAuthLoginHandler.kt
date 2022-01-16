package com.untilled.roadcapture.features.login

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.utils.type.SocialType

class KakaoOAuthLoginHandler(private val fragment: Fragment): (OAuthToken?, Throwable?) -> Unit {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun invoke(token: OAuthToken?, error: Throwable?) {
        error?.let { onError(it) }
        token?.let { onSuccess(it) }
        onCancel()
    }

    private fun onError(error: Throwable?) = fragment.requireContext().run {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT)
    }

    private fun onSuccess(token: OAuthToken) {
        viewModel.saveOAuthToken(
            OAuthTokenArgs(
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        )
        viewModel.socialLogin(SocialType.KAKAO)
    }

    private fun onCancel() {

    }
}