package com.untilled.roadcapture.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.untilled.roadcapture.features.login.LoginFragment
import com.untilled.roadcapture.features.login.LoginViewModel
import com.untilled.roadcapture.features.login.NaverOAuthLoginHandler
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
    fun provideNaverOAuthLoginHandler(fragment: Fragment): OAuthLoginHandler {
        return NaverOAuthLoginHandler(fragment)
    }
}
