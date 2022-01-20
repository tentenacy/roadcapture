package com.untilled.roadcapture.di

import com.google.gson.Gson
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
}