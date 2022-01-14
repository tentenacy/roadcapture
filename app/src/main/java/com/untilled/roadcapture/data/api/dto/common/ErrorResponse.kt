package com.untilled.roadcapture.data.api.dto.common

data class ErrorResponse(
    var code: String,
    var status: Int,
    var message: String,
    var errors: List<FieldError>,
) {
    data class FieldError(
        var filed: String,
        var value: String,
        var reason: String,
    )
}
