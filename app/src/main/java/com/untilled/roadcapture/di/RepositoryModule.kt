package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.datasource.dao.paging.follower.FollowersDao
import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.album.UserAlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.PictureCommentsPagingSource
import com.untilled.roadcapture.data.datasource.paging.follower.FollowersPagingSource
import com.untilled.roadcapture.data.datasource.paging.follower.FollowersRemoteMediator
import com.untilled.roadcapture.data.datasource.paging.follower.FollowingsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumRepositoryImpl
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepositoryImpl
import com.untilled.roadcapture.data.repository.comment.paging.CommentPagingRepository
import com.untilled.roadcapture.data.repository.comment.paging.CommentPagingRepositoryImpl
import com.untilled.roadcapture.data.repository.follower.FollowRepository
import com.untilled.roadcapture.data.repository.follower.FollowRepositoryImpl
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepository
import com.untilled.roadcapture.data.repository.follower.paging.FollowerPagingRepositoryImpl
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import com.untilled.roadcapture.data.repository.picture.PictureRepositoryImpl
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepository
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepositoryImpl
import com.untilled.roadcapture.data.repository.token.KotPrefTokenRepository
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.user.KotPrefUserRepository
import com.untilled.roadcapture.data.repository.user.LocalUserRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    companion object {
        @Singleton
        @Provides
        fun provideAlbumsPagingRepository(
            albumsPagingSource: AlbumsPagingSource,
            userAlbumsPagingSource: UserAlbumsPagingSource
        ): AlbumPagingRepository {
            return AlbumPagingRepositoryImpl(albumsPagingSource, userAlbumsPagingSource)
        }

        @Singleton
        @Provides
        fun provideAlbumCommentsPagingRepository(
            albumCommentsPagingSource: AlbumCommentsPagingSource,
            pictureCommentsPagingSource: PictureCommentsPagingSource
        ): CommentPagingRepository {
            return CommentPagingRepositoryImpl(
                albumCommentsPagingSource,
                pictureCommentsPagingSource
            )
        }

        @Singleton
        @Provides
        fun provideFollowersPagingRepository(
            followersPagingSource: FollowersPagingSource,
            followingsPagingSource: FollowingsPagingSource,
        ): FollowerPagingRepository {
            return FollowerPagingRepositoryImpl(followersPagingSource, followingsPagingSource)
        }

    }

    @Binds
    abstract fun provideAlbumRepository(
        repository: AlbumRepositoryImpl
    ): AlbumRepository

    @Binds
    abstract fun providePictureRepository(
        repository: PictureRepositoryImpl
    ): PictureRepository

    @Binds
    abstract fun provideSearchPlaceRepository(
        repository: SearchPlaceRepositoryImpl
    ): SearchPlaceRepository

    @Binds
    abstract fun provideUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun provideFollowRepository(
        repository: FollowRepositoryImpl
    ): FollowRepository

    @Binds
    abstract fun provideTokenRepository(
        repository: KotPrefTokenRepository
    ): LocalTokenRepository

    @Binds
    abstract fun provideLocalUserRepository(
        repository: KotPrefUserRepository
    ): LocalUserRepository
}