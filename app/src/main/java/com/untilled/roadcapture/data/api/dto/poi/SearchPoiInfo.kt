package com.untilled.roadcapture.data.api.dto.poi


data class SearchPoiInfo(
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)
