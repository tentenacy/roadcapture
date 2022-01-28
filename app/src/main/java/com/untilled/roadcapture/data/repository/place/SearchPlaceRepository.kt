package com.untilled.roadcapture.data.repository.place

import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.data.datasource.api.ext.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.ext.dto.poi.SearchPlaceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchPlaceRepository {
    suspend fun getSearchLocation(
        page: Int =  1,
        count: Int = 10,
        keyword: String
    ): Response<SearchPlaceResponse>

    fun getReverseGeoCode(
        lat: String,
        lon: String,
    ) : Single<TmapAddressInfoResponse>
}