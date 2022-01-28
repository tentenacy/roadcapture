package com.untilled.roadcapture.data.repository.place

import com.untilled.roadcapture.data.datasource.api.ext.TmapService
import com.untilled.roadcapture.data.datasource.api.ext.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.ext.dto.poi.SearchPlaceResponse
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
        page: Int,
        count: Int,
        keyword: String
    ): Response<SearchPlaceResponse>  =
        service.getSearchPlace(keyword = keyword)

    override fun getReverseGeoCode(
        lat: String,
        lon: String,
    ) : Single<TmapAddressInfoResponse> =
        service.getReverseGeoCode(lat = lat, lon = lon)
}
