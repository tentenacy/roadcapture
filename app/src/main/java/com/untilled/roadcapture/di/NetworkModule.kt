package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.api.RoadCaptureService
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
}