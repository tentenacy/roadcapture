package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumRepositoryImpl
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import com.untilled.roadcapture.data.repository.picture.PictureRepositoryImpl
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepository
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideAlbumRepository(
        albumsRepositoryImpl: AlbumRepositoryImpl
    ) : AlbumRepository

    @Binds
    abstract fun providePictureRepository(
        pictureRepositoryImpl: PictureRepositoryImpl
    ) : PictureRepository

    @Binds
    abstract fun provideSearchPlaceRepository(
        searchPlaceRepositoryImpl: SearchPlaceRepositoryImpl
    ) : SearchPlaceRepository
}