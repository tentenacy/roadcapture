package com.untilled.roadcapture.data.datasource.api

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.datasource.api.dto.address.AddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.dto.place.SearchPlaceResponse
import com.untilled.roadcapture.utils.constant.url.TmapUrlConstant
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
        @Query("callback") callback: String? = null,
        @Query("count") count: Int = 20,
        @Query("searchKeyword") keyword: String,
        @Query("areaLLCode") areaLLCode: String? = null,
        @Query("areaLMCode") areaLMCode: String? = null,
        @Query("resCoordType") resCoordType: String? = null,
        @Query("searchType") searchType: String? = null,
        @Query("multiPoint") multiPoint: String? = null,
        @Query("searchtypCd") searchtypCd: String? = null,
        @Query("radius") radius: String? = null,
        @Query("reqCoordType") reqCoordType: String? = null,
        @Query("centerLon") centerLon: String? = null,
        @Query("centerLat") centerLat: String? = null
    ): Response<SearchPlaceResponse>

    @GET(TmapUrlConstant.GET_TMAP_REVERSE_GEO_CODE)
    suspend fun getReverseGeoCode(
        @Header("appKey") appKey: String = BuildConfig.API_EXT_TMAP_KEY,
        @Query("version") version: Int = 1,
        @Query("callback") callback: String? = null,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("coordType") coordType: String? = null,
        @Query("addressType") addressType: String? = null
    ): Response<AddressInfoResponse>

    companion object {
        fun create(retrofitBuilder: Retrofit.Builder): TmapService {
            return retrofitBuilder
                .baseUrl(TmapUrlConstant.TMAP_URL)
                .build()
                .create()
        }
    }
}