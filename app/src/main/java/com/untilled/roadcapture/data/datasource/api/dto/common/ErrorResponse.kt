package com.untilled.roadcapture.data.datasource.api.dto.common

data class ErrorResponse(
    var code: String = "",
    var status: Int = 0,
    var message: String = "",
    var errors: List<FieldError> = emptyList(),
) {
    data class FieldError(
        var filed: String = "",
        var value: String = "",
        var reason: String = "",
    )
}
