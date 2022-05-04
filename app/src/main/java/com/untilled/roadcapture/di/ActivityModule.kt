package com.untilled.roadcapture.di

import com.untilled.roadcapture.features.common.LoadingDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {

    @Provides
    fun provideLoadingDialog(): LoadingDialog = LoadingDialog()
}