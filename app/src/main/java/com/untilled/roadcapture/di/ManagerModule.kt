package com.untilled.roadcapture.di

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.nhn.android.naverlogin.OAuthLogin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class ManagerModule {

    @Provides
    fun provideFacebookCallbackManager(): CallbackManager {
        return CallbackManager.Factory.create()
    }

    @Provides
    fun provideFacebookLoginManager(): LoginManager {
        return LoginManager.getInstance()
    }

    @Provides
    fun provideNaverLoginManager(): OAuthLogin {
        return OAuthLogin.getInstance()
    }
}