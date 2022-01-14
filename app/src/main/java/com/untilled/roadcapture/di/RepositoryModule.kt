package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumRepositoryImpl
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import com.untilled.roadcapture.data.repository.picture.PictureRepositoryImpl
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepository
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepositoryImpl
import com.untilled.roadcapture.data.repository.token.KotPrefTokenRepository
import com.untilled.roadcapture.data.repository.token.LocalTokenRepository
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
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
    abstract fun provideTokenRepository(
        repository: KotPrefTokenRepository
    ): LocalTokenRepository
}