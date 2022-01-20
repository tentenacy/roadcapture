package com.untilled.roadcapture.di

import com.google.gson.Gson
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ConverterModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideAlbumsMapper(): AlbumsMapper {
        return AlbumsMapper()
    }
}