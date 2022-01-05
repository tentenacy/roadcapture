package com.untilled.roadcapture.data.dto.poi


data class SearchPoiInfo(
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)
