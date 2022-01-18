package com.untilled.roadcapture.di

import android.app.Application
import androidx.work.WorkManager
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.application.GlobalApplication
import com.untilled.roadcapture.network.subject.OAuthLoginManagerSubject
import com.untilled.roadcapture.utils.manager.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@InstallIn(SingletonComponent::class)
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
        fun provideGoogleSignInClient(application: Application): GoogleSignInClient {
            return GoogleSignIn.getClient(
                application.applicationContext,
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

        @Provides
        fun provideAuthApiClient(): AuthApiClient {
            return AuthApiClient.instance
        }

        @Provides
        fun provideWorkManager(application: Application): WorkManager {
            return WorkManager.getInstance(application)
        }
    }

    @Binds
    @IntoMap
    @StringKey("kakao")
    abstract fun provideKakaoLoginManager(kakaoLoginManager: KakaoLoginManager): OAuthLoginManagerSubject

    @Binds
    @IntoMap
    @StringKey("naver")
    abstract fun provideNaverLoginManager(naverLoginManager: NaverLoginManager): OAuthLoginManagerSubject

    @Binds
    @IntoMap
    @StringKey("google")
    abstract fun provideGoogleLoginManager(googleLoginManager: GoogleLoginManager): OAuthLoginManagerSubject

    @Binds
    @IntoMap
    @StringKey("facebook")
    abstract fun provideFacebookLoginManager(facebookLoginManager: FacebookLoginManager): OAuthLoginManagerSubject
}