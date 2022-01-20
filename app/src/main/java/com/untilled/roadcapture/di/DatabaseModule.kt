package com.untilled.roadcapture.di

import android.content.Context
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.datasource.database.PagingDatabase
import com.untilled.roadcapture.data.datasource.database.PictureDatabase
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
    fun providePagingDatabase(@ApplicationContext context: Context): PagingDatabase {
        return PagingDatabase.getInstance(context)
    }
}