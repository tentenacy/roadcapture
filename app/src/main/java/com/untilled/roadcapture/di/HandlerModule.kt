package com.untilled.roadcapture.di

import android.app.Activity
import android.content.Context
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.kakao.sdk.auth.model.OAuthToken
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.untilled.roadcapture.features.login.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
class HandlerModule {

    @Provides
    fun provideNaverOAuthLoginHandler(fragment: Fragment, naverLoginManager: OAuthLogin): NaverOAuthLoginHandler {
        return NaverOAuthLoginHandler(fragment, naverLoginManager)
    }

    @Provides
    fun provideFacebookOAuthLoginHandler(fragment: Fragment): FacebookOAuthLoginHandler {
        return FacebookOAuthLoginHandler(fragment)
    }

    @Provides
    fun provideKakaoOAuthLoginHandler(fragment: Fragment): KakaoOAuthLoginHandler {
        return KakaoOAuthLoginHandler(fragment)
    }

    @Provides
    fun provideGoogleOAuothLoginHandler(fragment: Fragment): GoogleOAuthLoginHandler {
        return GoogleOAuthLoginHandler(fragment)
    }
}
