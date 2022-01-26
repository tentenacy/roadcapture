package com.untilled.roadcapture.di

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(FragmentComponent::class)
@Module
class ViewModule {

    @Provides
    fun provideCustomDivider(): CustomDivider {
        return CustomDivider(2.5f, 1f, Color.parseColor("#EFEFEF"))
    }
}