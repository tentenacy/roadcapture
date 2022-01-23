package com.untilled.roadcapture.utils.constant.url

object RoadCapturePathConstant {

    const val GET_ALBUMS = "/albums"
    const val GET_ALBUM = "/albums/{id}"
    const val GET_MY_ALBUMS = "/users/me/albums"

    const val POST_ALBUM_LIKE = "/albums/{albumId}/likes"
    const val DELETE_ALBUM_LIKE = "/albums/{albumId}/likes"

    const val GET_ALBUM_COMMENTS = "/albums/{albumId}/pictures/comments"
    const val GET_PICTURE_COMMENTS = "/pictures/{pictureId}/comments"

    const val GET_MY_INFO = "/users/me"
    const val GET_USER_DETAIL = "/users/details"
    const val GET_USER_INFO = "/users/{id}"
    const val GET_USER_FOLLOWER = "/users/{userId}/followers/from"
    const val GET_USER_FOLLOWING = "/users/{userId}/followers/to"
    const val GET_USER_ALBUMS = "/users/{userId}/albums"
    const val GET_MY_FOLLOWER = "/followers/from"
    const val GET_MY_FOLLOWING = "/followers/to"

    const val GET_FOLLOWERS_TO_ALBUMS = "/followers/to/albums"

    const val POST_FOLLOWERS_FOLLOW = "/followers/{toUserId}"
    const val DELETE_FOLLOWERS_FOLLOW = "/followers/{toUserId}"

    const val POST_LOGIN = "/users/token"
    const val POST_SIGNUP = "/users"
    const val POST_SOCIAL_LOGIN = "/users/social/{socialType}/token"
    const val POST_SOCIAL_SIGNUP = "/users/social/{socialType}"
    const val POST_REISSUE = "/users/token/reissue"
}