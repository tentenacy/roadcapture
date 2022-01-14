package com.untilled.roadcapture.data.repository.place

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.datasource.api.dto.place.SearchPlaceResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchPlaceRepository {
    suspend fun getSearchLocation(
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
}