package com.untilled.roadcapture.di

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
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

    @Provides
    fun provideActivityResultFactory(activity: Activity): ActivityResultFactory<Intent, ActivityResult> {
        return ActivityResultFactory.registerActivityForResult(activity as AppCompatActivity)
    }
}