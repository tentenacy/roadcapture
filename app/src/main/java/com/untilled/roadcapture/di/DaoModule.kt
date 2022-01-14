package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.datasource.dao.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import dagger.multibindings.StringKey
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DaoModule {

    @IntoMap
    @Binds
    @StringKey("kakao")
    abstract fun provideKakaoOAuthTokenDao(dao: KakaoOAuthTokenDao): LocalOAuthTokenDao

    @IntoMap
    @Binds
    @StringKey("google")
    abstract fun provideGoogleOAuthTokenDao(dao: GoogleOAuthTokenDao): LocalOAuthTokenDao

    @IntoMap
    @Binds
    @StringKey("naver")
    abstract fun provideNaverOAuthTokenDao(dao: NaverOAuthTokenDao): LocalOAuthTokenDao

    @IntoMap
    @Binds
    @StringKey("facebook")
    abstract fun provideFacebookOAuthTokenDao(dao: FacebookOAuthTokenDao): LocalOAuthTokenDao

    @Binds
    abstract fun provideLocalTokenDao(dao: TokenDao): LocalTokenDao
}