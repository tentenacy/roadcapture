package com.untilled.roadcapture.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.untilled.roadcapture.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class ClientModule {

    @Provides
    fun provideGoogleSignInClient(activity: Activity): GoogleSignInClient {
        return GoogleSignIn.getClient(
            activity,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.SOCIAL_GOOGLE_CLIENT_ID).requestProfile().requestEmail()
                .build()
        )
    }
}