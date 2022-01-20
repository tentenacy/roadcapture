package com.untilled.roadcapture.di

import com.google.gson.Gson
import com.untilled.roadcapture.data.entity.mapper.AlbumsMapper
import com.untilled.roadcapture.data.entity.mapper.CommentsMapper
import com.untilled.roadcapture.data.entity.mapper.FollowersMapper
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

    @Provides
    @Singleton
    fun provideCommentsMapper(): CommentsMapper {
        return CommentsMapper()
    }

    @Provides
    @Singleton
    fun provideFollowersMapper(): FollowersMapper {
        return FollowersMapper()
    }

}