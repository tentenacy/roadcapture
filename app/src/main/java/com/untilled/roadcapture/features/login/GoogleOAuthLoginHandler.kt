package com.untilled.roadcapture.features.login

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.utils.type.SocialType
import java.lang.Exception

class GoogleOAuthLoginHandler(private val fragment: Fragment): (ActivityResult) -> Unit {

    private val viewModel by lazy {
        ViewModelProvider(fragment).get(LoginViewModel::class.java)
    }

    override fun invoke(result: ActivityResult) {
        try {
            if(result.resultCode == Activity.RESULT_OK) onSuccess(GoogleSignIn.getSignedInAccountFromIntent(result.data))
        } catch (e: ApiException) {
            onError(e)
        } finally {
            onCancel()
        }
    }

    private fun onSuccess(completedTask: Task<GoogleSignInAccount>) {
        val account = completedTask.getResult(ApiException::class.java)

        viewModel.saveOAuthToken(
            OAuthTokenArgs(
                accessToken = account.idToken!!,
                refreshToken = null,
                socialType = SocialType.GOOGLE.name,
            )
        )

        viewModel.socialLogin(SocialType.GOOGLE)
    }

    private fun onCancel() {

    }

    private fun onError(e: Exception) {
        when(e) {
            is ApiException -> {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Logger.w("signInResult:failed code=${e.statusCode}")
            }
        }
    }
}