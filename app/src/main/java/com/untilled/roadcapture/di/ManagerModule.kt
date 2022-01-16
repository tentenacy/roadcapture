package com.untilled.roadcapture.di

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
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
}