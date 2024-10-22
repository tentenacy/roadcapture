package com.untilled.roadcapture.di

import android.content.Context
import com.untilled.roadcapture.data.datasource.dao.*
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.AlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.UserAlbumsDao
import com.untilled.roadcapture.data.datasource.dao.paging.album.UserAlbumsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsDao
import com.untilled.roadcapture.data.datasource.dao.paging.comment.AlbumCommentsRemoteKeysDao
import com.untilled.roadcapture.data.datasource.dao.paging.follower.*
import com.untilled.roadcapture.data.datasource.database.PagingDatabase
import com.untilled.roadcapture.data.datasource.database.PictureDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DaoModule {

    companion object {

        @Singleton
        @Provides
        fun providePictureDao(pictureDatabase: PictureDatabase): PictureDao = pictureDatabase.pictureDao()

        @Singleton
        @Provides
        fun provideAlbumsDao(pagingDatabase: PagingDatabase): AlbumsDao {
            return pagingDatabase.albumsDao()
        }

        @Singleton
        @Provides
        fun provideAlbumsRemoteKeysDao(pagingDatabase: PagingDatabase): AlbumsRemoteKeysDao {
            return pagingDatabase.albumsRemoteKeysDao()
        }

        @Singleton
        @Provides
        fun provideAlbumCommentsDao(pagingDatabase: PagingDatabase): AlbumCommentsDao {
            return pagingDatabase.albumCommentsDao()
        }

        @Singleton
        @Provides
        fun provideAlbumCommentsKeysDao(pagingDatabase: PagingDatabase): AlbumCommentsRemoteKeysDao {
            return pagingDatabase.albumCommentsKeysDao()
        }

        @Singleton
        @Provides
        fun provideUserAlbumsDao(pagingDatabase: PagingDatabase): UserAlbumsDao {
            return pagingDatabase.userAlbumsDao()
        }

        @Singleton
        @Provides
        fun provideUserAlbumsRemoteKeysDao(pagingDatabase: PagingDatabase): UserAlbumsRemoteKeysDao {
            return pagingDatabase.userAlbumsKeysDao()
        }

        @Singleton
        @Provides
        fun provideFollowersDao(pagingDatabase: PagingDatabase): FollowersDao {
            return pagingDatabase.followersDao()
        }

        @Singleton
        @Provides
        fun provideFollowersRemoteKeysDao(pagingDatabase: PagingDatabase): FollowersRemoteKeysDao {
            return pagingDatabase.followersKeysDao()
        }

        @Singleton
        @Provides
        fun provideFollowingsDao(pagingDatabase: PagingDatabase): FollowingsDao {
            return pagingDatabase.followingsDao()
        }

        @Singleton
        @Provides
        fun provideFollowingsRemoteKeysDao(pagingDatabase: PagingDatabase): FollowingsRemoteKeysDao {
            return pagingDatabase.followingsKeysDao()
        }

        @Singleton
        @Provides
        fun provideFollowingsSortByAlbumDao(pagingDatabase: PagingDatabase): FollowingsSortByAlbumDao {
            return pagingDatabase.followingsSortByAlbumDao()
        }

        @Singleton
        @Provides
        fun provideFollowingsSortByAlbumRemoteKeysDao(pagingDatabase: PagingDatabase): FollowingsSortByAlbumRemoteKeysDao {
            return pagingDatabase.followingsSortByAlbumRemoteKeysDao()
        }
    }

    @Binds
    abstract fun provideLocalOAuthTokenDao(dao: KotPrefOAuthTokenDao): LocalOAuthTokenDao

    @Binds
    abstract fun provideLocalTokenDao(dao: TokenDao): LocalTokenDao

    @Binds
    abstract fun provideLocalUserDao(dao: UserDao): LocalUserDao
}