package com.untilled.roadcapture.features.login.handler

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.features.login.LoginViewModel
import com.untilled.roadcapture.utils.type.SocialType

class FacebookOAuthLoginHandler(private val fragment: Fragment) : FacebookCallback<LoginResult> {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    //로그인에 성공하면 LoginResult 매개변수에 새로운 AccessToken과 최근에 부여되거나 거부된 권한이 포함됩니다.
    //맞춤 설정할 수 있는 속성에는 LoginBehavior, DefaultAudience, ToolTipPopup.Style 및 LoginButton의 권한이 포함되어 있습니다.
    override fun onSuccess(result: LoginResult?) {
        result?.let {
            viewModel.saveOAuthToken(
                OAuthTokenArgs(
                    accessToken = it.accessToken.token,
                    refreshToken = null,
                    socialType = SocialType.FACEBOOK.name,
                )
            )

            viewModel.socialLogin(SocialType.FACEBOOK)
        }
    }

    override fun onCancel() {
    }

    override fun onError(error: FacebookException?) {
        fragment.requireContext().run {
            Toast.makeText(this, "error: $error", Toast.LENGTH_SHORT).show()
        }
    }
}