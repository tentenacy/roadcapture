package com.untilled.roadcapture.di

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.mobsandgeeks.saripaar.Validator
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.features.common.LoadingDialog
import com.untilled.roadcapture.features.login.handler.FacebookOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.GoogleOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.KakaoOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.NaverOAuthLoginHandler
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
class FragmentModule {

    @FragmentScoped
    @Provides
    fun provideCustomDivider(): CustomDivider {
        return CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
    }

    @Provides
    fun provideValidator(fragment: Fragment): Validator {
        return Validator(fragment)
    }

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