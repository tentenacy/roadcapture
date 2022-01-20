package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.datasource.paging.album.AlbumsPagingSource
import com.untilled.roadcapture.data.datasource.paging.comment.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumRepositoryImpl
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepositoryImpl
import com.untilled.roadcapture.data.repository.comment.paging.AlbumCommentPagingRepository
import com.untilled.roadcapture.data.repository.comment.paging.AlbumCommentPagingRepositoryImpl
import com.untilled.roadcapture.data.repository.follow.FollowRepository
import com.untilled.roadcapture.data.repository.follow.FollowRepositoryImpl
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
        fun provideAlbumsPagingRepository(pagingSource: AlbumsPagingSource): AlbumPagingRepository {
            return AlbumPagingRepositoryImpl(pagingSource)
        }

        @Singleton
        @Provides
        fun provideAlbumCommentsPagingRepository(pagingSource: AlbumCommentsPagingSource): AlbumCommentPagingRepository {
            return AlbumCommentPagingRepositoryImpl(pagingSource)
        }
    }

    @Binds
    abstract fun provideAlbumRepository(
        repository: AlbumRepositoryImpl
    ) : AlbumRepository

    @Binds
    abstract fun providePictureRepository(
        repository: PictureRepositoryImpl
    ) : PictureRepository

    @Binds
    abstract fun provideSearchPlaceRepository(
        repository: SearchPlaceRepositoryImpl
    ) : SearchPlaceRepository

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