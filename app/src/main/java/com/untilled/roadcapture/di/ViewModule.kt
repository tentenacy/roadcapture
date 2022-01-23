package com.untilled.roadcapture.di

import android.graphics.Color
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ViewModule {

    @Singleton
    @Provides
    fun provideCustomDivider(): CustomDivider {
        return CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
    }
}