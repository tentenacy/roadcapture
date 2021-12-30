package com.untilled.roadcapture.di

import android.content.Context
import com.untilled.roadcapture.data.dao.AlbumDao
import com.untilled.roadcapture.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context):
            AppDataBase = AppDataBase.getInstance(context)

    @Singleton
    @Provides
    fun provideAlbumDao(appDataBase: AppDataBase): AlbumDao = appDataBase.albumDao()
}