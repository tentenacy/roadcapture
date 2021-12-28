package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.repository.albums.AlbumsRepository
import com.untilled.roadcapture.data.repository.albums.AlbumsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun providesRepository(
        albumsRepositoryImpl: AlbumsRepositoryImpl
    ) : AlbumsRepository
}