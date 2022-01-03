package com.untilled.roadcapture.data.dto.search


data class SearchPoiInfo(
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)
