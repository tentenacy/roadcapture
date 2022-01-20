package com.untilled.roadcapture.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.TmapService
import com.untilled.roadcapture.network.interceptor.OAuthTokenInterceptor
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
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton
import com.google.gson.Gson
import com.untilled.roadcapture.data.datasource.dao.LocalTokenDao
import com.untilled.roadcapture.utils.converter.GsonLocalDateTimeAdapter


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
    fun provideTokenInterceptor(localTokenDao: LocalTokenDao, gson: Gson): TokenInterceptor {
        return TokenInterceptor(localTokenDao, gson)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        oauthTokenInterceptor: OAuthTokenInterceptor,
    ): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(oauthTokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter()).create()

        return Retrofit.Builder()
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