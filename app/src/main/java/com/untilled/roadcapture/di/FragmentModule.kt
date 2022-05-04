package com.untilled.roadcapture.di

import android.graphics.Color
import com.untilled.roadcapture.features.common.LoadingDialog
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(FragmentComponent::class)
@Module
class FragmentModule {

    @Provides
    fun provideCustomDivider(): CustomDivider {
        return CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
    }
}