package com.untilled.roadcapture.data.datasource.api.dto.common

import javax.net.ssl.HttpsURLConnection

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    /**
     * COMMON
     */
    INVALID_INPUT_VALUE(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "CMM-001",
        "잘못된 입력입니다."
    ),
    METHOD_NOT_ALLOWED(
        HttpsURLConnection.HTTP_BAD_METHOD,
        "CMM-002",
        "Method Not Allowed"
    ),
    ENTITY_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "CMM-003",
        "Entity Not Found"
    ),
    INTERNAL_SERVER_ERROR(
        HttpsURLConnection.HTTP_INTERNAL_ERROR,
        "CMM-004",
        "Server Error"
    ),
    INVALID_TYPE_VALUE(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "CMM-005",
        "Invalid Type Value"
    ),
    HANDLE_ACCESS_DENIED(HttpsURLConnection.HTTP_FORBIDDEN, "CMM-006", "접근이 거부되었습니다."),
    JSON_WRITE_ERROR(
        HttpsURLConnection.HTTP_UNAUTHORIZED,
        "CMM-007",
        "JSON content that are not pure I/O problems"
    ),

    /**
     * IO
     */
    FILE_CONVERT_FAILED(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "IO-001",
        "파일을 변환할 수 없습니다."
    ),
    INVALID_FILE_FORMAT(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "IO-002",
        "잘못된 형식의 파일입니다."
    ),
    CLOUD_COMMUNICATION_ERROR(
        HttpsURLConnection.HTTP_INTERNAL_ERROR,
        "IO-003",
        "파일 업로드 중 오류가 발생했습니다."
    ),

    /**
     * SOCIAL
     */
    SOCIAL_COMMUNICATION_ERROR(
        HttpsURLConnection.HTTP_INTERNAL_ERROR,
        "SCL-001",
        "소셜 인증 과정 중 오류가 발생했습니다."
    ),
    SOCIAL_AGREEMENT_ERROR(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "SCL-002",
        "필수동의 항목에 대해 동의가 필요합니다."
    ),
    INVALID_SOCIAL_TYPE(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "SCL-003",
        "알 수 없는 소셜 타입입니다."
    ),
    SOCIAL_TOKEN_VALID_FAILED(HttpsURLConnection.HTTP_UNAUTHORIZED, "SCR-004", "소셜 액세스 토큰 검증에 실패했습니다."),

    /**
     * SECURITY
     */
    ACCESS_TOKEN_ERROR(
        HttpsURLConnection.HTTP_UNAUTHORIZED,
        "SCR-001",
        "액세스 토큰이 만료되거나 잘못된 값입니다."
    ),
    REFRESH_TOKEN_ERROR(
        HttpsURLConnection.HTTP_UNAUTHORIZED,
        "SCR-002",
        "리프레시 토큰이 만료되거나 잘못된 값입니다."
    ),
    TOKEN_PARSE_ERROR(HttpsURLConnection.HTTP_UNAUTHORIZED, "SCR-003", "해석할 수 없는 토큰입니다."),

    /**
     * BUSINESS
     */
    EMAIL_LOGIN_FAIL(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-001",
        "존재하지 않는 계정이거나, 잘못된 비밀번호입니다."
    ),
    ALREADY_SIGNEDUP(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-002", "이미 가입한 사용자입니다."),
    USER_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-003",
        "사용자가 존재하지 않습니다."
    ),
    USER_NOT_AUTHENTICATION(
        HttpsURLConnection.HTTP_UNAUTHORIZED,
        "BIZ-004",
        "인증된 사용자가 아닙니다."
    ),
    ALBUM_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-005", "앨범이 존재하지 않습니다."), PICTURE_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-006",
        "사진이 존재하지 않습니다."
    ),
    PLACE_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-007", "장소가 존재하지 않습니다."), COMMENT_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-008",
        "댓글이 존재하지 않습니다."
    ),
    ALREADY_LIKE(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-009", "이미 좋아요를 했습니다."), LIKE_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-010",
        "좋아요가 존재하지 않습니다."
    ),
    USER_OWN_ALBUM_ERROR(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-011",
        "사용자의 앨범이 아닙니다."
    ),
    PICTURE_BELONG_ERROR(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-012",
        "앨범의 사진이 아닙니다."
    ),
    THUMBNAIL_NON_UNIQUE(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-013",
        "앨범 썸네일이 유일하지 않습니다."
    ),
    MULTIPART_KEY_MISMATCH(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-014",
        "업로드 파일 키와 일치하는 데이터가 없습니다."
    ),
    PICTURE_MULTIPART_REQUIRED(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-015",
        "사진을 생성하기 위한 파일이 필요합니다."
    ),
    USERTOFOLLOW_NOT_FOUND(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-016",
        "팔로우할 사용자가 존재하지 않습니다."
    ),
    ALREADY_FOLLOW(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-017", "이미 팔로우를 했습니다."), FOLLOW_MYSELF_ERROR(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-018",
        "자신은 팔로우할 수 없습니다."
    ),
    UNFOLLOW_MYSELF_ERROR(
        HttpsURLConnection.HTTP_BAD_REQUEST,
        "BIZ-019",
        "자신은 언팔로우할 수 없습니다."
    ),
    FOLLOWER_NOT_FOUND(HttpsURLConnection.HTTP_BAD_REQUEST, "BIZ-020", "사용자를 팔로우하지 않았습니다.");


}