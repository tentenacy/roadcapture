package com.untilled.roadcapture.data.repository.place

import com.untilled.roadcapture.data.api.TmapService
import com.untilled.roadcapture.data.dto.place.SearchPlaceResponse
import retrofit2.Response
import javax.inject.Inject

class SearchPlaceRepositoryImpl
    @Inject constructor(
        private val service: TmapService
    ): SearchPlaceRepository{
    override suspend fun getSearchLocation(
        appKey: String,
        version: Int,
        callback: String?,
        count: Int,
        keyword: String,
        areaLLCode: String?,
        areaLMCode: String?,
        resCoordType: String?,
        searchType: String?,
        multiPoint: String?,
        searchtypCd: String?,
        radius: String?,
        reqCoordType: String?,
        centerLon: String?,
        centerLat: String?
    ): Response<SearchPlaceResponse>  =
        service.getSearchPlace(keyword = keyword)
}