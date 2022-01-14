package com.untilled.roadcapture.data.url

object RoadCaptureUrl {

    const val GET_ALBUMS = "/albums"
    const val GET_ALBUM = "/albums/{id}"

    const val GET_ALBUM_COMMENTS = "/albums/{albumId}/pictures/comments"
    const val GET_PICTURE_COMMENTS = "/pictures/{pictureId}/comments"

    const val POST_SOCIAL_LOGIN = "/users/social/{socialType}/token"
    const val POST_SOCIAL_SIGNUP = "/users/social/{socialType}"
}