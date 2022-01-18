package com.untilled.roadcapture.di

import android.app.Activity
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class ManagerModule {

    @Provides
    fun provideCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

    @Provides
    fun provideLoginManager(): LoginManager {
        return LoginManager.getInstance()
    }

    @Provides
    fun provideOAuthLogin(): OAuthLogin {
        return OAuthLogin.getInstance()
    }

    @Provides
    fun provideGoogleSignInClient(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(
            activity,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.SOCIAL_GOOGLE_CLIENT_ID)
                .requestEmail().requestProfile()
                .build()
        )
    }
}