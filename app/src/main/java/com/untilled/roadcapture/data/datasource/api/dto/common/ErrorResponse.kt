package com.untilled.roadcapture.data.datasource.api.dto.common

data class ErrorResponse(
    val code: String,
    val status: Int,
    val message: String,
    val errors: List<FieldError>,
) {
    data class FieldError(
        val filed: String,
        val value: String,
        val reason: String,
    )

    fun isEmpty() = code.isBlank() || status == 0 || message.isBlank()
    fun isNotEmpty() = !isEmpty()
}
