package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.datasource.dao.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@InstallIn(SingletonComponent::class)
@Module
abstract class DaoModule {

    @Binds
    abstract fun provideLocalOAuthTokenDao(dao: KotPrefOAuthTokenDao): LocalOAuthTokenDao

    @Binds
    abstract fun provideLocalTokenDao(dao: TokenDao): LocalTokenDao

    @Binds
    abstract fun provideLocalUserDao(dao: UserDao): LocalUserDao
}