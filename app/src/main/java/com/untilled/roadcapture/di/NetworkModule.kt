package com.untilled.roadcapture.di

import com.untilled.roadcapture.data.datasource.api.RoadCaptureApi
import com.untilled.roadcapture.data.datasource.api.TmapService
import com.untilled.roadcapture.network.interceptor.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
    fun provideTokenInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(httpLoggingInterceptor: HttpLoggingInterceptor, authenticationInterceptor: AuthenticationInterceptor): Retrofit.Builder {

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
    }

    @Singleton
    @Provides
    fun provideRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder.build()
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