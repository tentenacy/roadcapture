package com.untilled.roadcapture.di

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class ResultApiModule {

    @Provides
    fun provideActivityResultFactory(activity: Activity): ActivityResultFactory<Intent, ActivityResult> {
        return ActivityResultFactory.registerActivityForResult(activity as AppCompatActivity)
    }
}