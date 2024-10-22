package com.untilled.roadcapture.di

import com.google.gson.GsonBuilder
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.ext.TmapService
import com.untilled.roadcapture.network.interceptor.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.utils.converter.GsonLocalDateTimeAdapter
import retrofit2.converter.scalars.ScalarsConverterFactory


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (com.untilled.roadcapture.BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(localTokenDao: LocalTokenDao): TokenInterceptor {
        return TokenInterceptor(localTokenDao)
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter()).create()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideRoadCaptureApi(retrofitBuilder: Retrofit.Builder): RoadCaptureApi {
        return RoadCaptureApi.create(retrofitBuilder)
    }

    @Singleton
    @Provides
    fun provideTmapService(retrofitBuilder: Retrofit.Builder): TmapService {
        return TmapService.create(retrofitBuilder)
    }
}