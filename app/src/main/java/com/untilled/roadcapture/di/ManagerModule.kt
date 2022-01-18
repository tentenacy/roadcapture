package com.untilled.roadcapture.di

import android.app.Activity
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.utils.manager.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@InstallIn(ActivityComponent::class)
@Module
abstract class ManagerModule {

    companion object {

        @Provides
        fun provideCallbackManager(): CallbackManager {
            return CallbackManager.Factory.create()
        }

        @Provides
        fun provideLoginManager(): LoginManager {
            return LoginManager.getInstance()
        }

        @Provides
        fun provideOAuthLogin(): OAuthLogin {
            return OAuthLogin.getInstance()
        }

        @Provides
        fun provideGoogleSignInClient(activity: Activity): GoogleSignInClient {
            return GoogleSignIn.getClient(
                activity,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.SOCIAL_GOOGLE_CLIENT_ID)
                    .requestEmail().requestProfile()
                    .build()
            )
        }

        @Provides
        fun provideUserApiClient(): UserApiClient {
            return UserApiClient.instance
        }
    }

    @Binds
    @IntoMap
    @StringKey("kakao")
    abstract fun provideKakaoLoginManager(kakaoLoginManager: KakaoLoginManager): OAuthLoginManager

    @Binds
    @IntoMap
    @StringKey("naver")
    abstract fun provideNaverLoginManager(naverLoginManager: NaverLoginManager): OAuthLoginManager

    @Binds
    @IntoMap
    @StringKey("google")
    abstract fun provideGoogleLoginManager(googleLoginManager: GoogleLoginManager): OAuthLoginManager

    @Binds
    @IntoMap
    @StringKey("facebook")
    abstract fun provideFacebookLoginManager(facebookLoginManager: FacebookLoginManager): OAuthLoginManager
}