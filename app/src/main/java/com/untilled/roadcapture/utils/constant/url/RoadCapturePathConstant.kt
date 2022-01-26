package com.untilled.roadcapture.utils.constant.url

object RoadCapturePathConstant {

    const val GET_ALBUMS = "/albums"
    const val GET_ALBUM = "/albums/{id}"
    const val GET_MY_ALBUMS = "/users/me/albums"
    const val POST_ALBUM = "/albums/temp"

    const val POST_ALBUM_LIKE = "/albums/{albumId}/likes"
    const val DELETE_ALBUM_LIKE = "/albums/{albumId}/likes"

    const val GET_ALBUM_COMMENTS = "/albums/{albumId}/pictures/comments"
    const val GET_PICTURE_COMMENTS = "/pictures/{pictureId}/comments"
    const val POST_PICTURE_COMMENTS = "/pictures/{pictureId}/comments"

    const val GET_MY_STUDIO_USER = "/users/me"
    const val GET_USER_DETAIL = "/users/details"
    const val GET_STUDIO_USER = "/users/{id}"
    const val GET_USER_FOLLOWERS = "/users/{userId}/followers/from"
    const val GET_USER_FOLLOWINGS = "/users/{userId}/followers/to"
    const val GET_USER_ALBUMS = "/users/{userId}/albums"
    const val GET_FOLLOWERS = "/followers/from"
    const val GET_FOLLOWINGS = "/followers/to"

    const val GET_FOLLOWINGS_ALBUMS = "/followers/to/albums"

    const val GET_FOLLOWINGS_SORT_BY_ALBUM = "/followers/to/sort-by-album"
    const val POST_FOLLOWERS = "/followers/{toUserId}"
    const val DELETE_FOLLOWERS = "/followers/{toUserId}"

    const val POST_LOGIN = "/users/token"
    const val POST_SIGNUP = "/users"
    const val POST_SOCIAL_LOGIN = "/users/social/{socialType}/token"
    const val POST_SOCIAL_SIGNUP = "/users/social/{socialType}"
    const val POST_REISSUE = "/users/token/reissue"
}