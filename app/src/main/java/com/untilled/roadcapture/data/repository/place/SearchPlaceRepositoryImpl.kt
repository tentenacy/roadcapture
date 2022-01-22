package com.untilled.roadcapture.data.repository.place

import com.untilled.roadcapture.data.datasource.api.TmapService
import com.untilled.roadcapture.data.datasource.api.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.dto.place.SearchPlaceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    override fun getReverseGeoCode(
        lat: String,
        lon: String,
    ) : Single<TmapAddressInfoResponse> =
        service.getReverseGeoCode(lat = lat, lon = lon)
}
