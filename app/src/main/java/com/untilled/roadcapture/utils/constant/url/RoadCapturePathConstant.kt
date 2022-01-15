package com.untilled.roadcapture.utils.constant.url

object RoadCapturePathConstant {

    const val GET_ALBUMS = "/albums"
    const val GET_ALBUM = "/albums/{id}"

    const val GET_ALBUM_COMMENTS = "/albums/{albumId}/pictures/comments"
    const val GET_PICTURE_COMMENTS = "/pictures/{pictureId}/comments"

    const val GET_USER_DETAiL = "/users/details"

    const val POST_SOCIAL_LOGIN = "/users/social/{socialType}/token"
    const val POST_SOCIAL_SIGNUP = "/users/social/{socialType}"
    const val POST_REISSUE = "/users/token/reissue"
}