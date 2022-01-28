package com.untilled.roadcapture.data.datasource.api.ext.dto.poi

data class SearchPoiInfo(
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)
