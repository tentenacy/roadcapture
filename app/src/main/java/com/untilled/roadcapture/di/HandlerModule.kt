package com.untilled.roadcapture.di

import androidx.fragment.app.Fragment
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.features.login.handler.FacebookOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.GoogleOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.KakaoOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.NaverOAuthLoginHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
