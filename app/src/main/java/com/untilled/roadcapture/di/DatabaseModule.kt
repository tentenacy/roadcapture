package com.untilled.roadcapture.di

import android.content.Context
import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.datasource.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) :
            AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePictureDao(appDatabase: AppDatabase): PictureDao = appDatabase.pictureDao()
}