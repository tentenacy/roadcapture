package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.api.RoadCaptureService
import com.untilled.roadcapture.data.api.TmapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRoadCaptureService(): RoadCaptureService {
        return RoadCaptureService.create()
    }

    @Singleton
    @Provides
    fun provideTmapService(): TmapService {
        return TmapService.create()
    }
}