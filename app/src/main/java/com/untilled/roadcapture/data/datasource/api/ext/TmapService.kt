package com.untilled.roadcapture.data.datasource.api.ext

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.datasource.api.ext.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.ext.dto.poi.SearchPlaceResponse
import com.untilled.roadcapture.utils.constant.url.TmapUrlConstant
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TmapService {

    @GET(TmapUrlConstant.GET_TMAP_LOCATION)
    suspend fun getSearchPlace(
        @Header("appKey") appKey: String = BuildConfig.API_EXT_TMAP_KEY,
        @Query("version") version: Int = 1,
        @Query("page") page: Int = 1,
        @Query("count") count: Int = 10,
        @Query("searchKeyword") keyword: String,
    ): Response<SearchPlaceResponse>

    @GET(TmapUrlConstant.GET_TMAP_REVERSE_GEO_CODE)
    fun getReverseGeoCode(
        @Header("appKey") appKey: String = BuildConfig.API_EXT_TMAP_KEY,
        @Query("version") version: Int = 1,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("addressType") addressType: String? = "A10"
    ): Single<TmapAddressInfoResponse>

    companion object {
        fun create(retrofitBuilder: Retrofit.Builder): TmapService {
            return retrofitBuilder
                .baseUrl(TmapUrlConstant.TMAP_URL)
                .build()
                .create()
        }
    }
}