package com.untilled.roadcapture.di

import android.content.Context
import com.untilled.roadcapture.data.datasource.dao.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.AlbumsRxDao
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
    fun providePictureDatabase(@ApplicationContext context: Context) :
            PictureDatabase = PictureDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePictureDao(pictureDatabase: PictureDatabase): PictureDao = pictureDatabase.pictureDao()

    @Singleton
    @Provides
    fun providePagingDatabase(@ApplicationContext context: Context): PagingDatabase {
        return PagingDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideAlbumsRxDao(pagingDatabase: PagingDatabase): AlbumsRxDao {
        return pagingDatabase.albumsRxDao()
    }

    @Singleton
    @Provides
    fun provideAlbumsRemoteKeysDao(pagingDatabase: PagingDatabase): AlbumsRemoteKeysDao {
        return pagingDatabase.albumsRemoteKeysRxDao()
    }
}