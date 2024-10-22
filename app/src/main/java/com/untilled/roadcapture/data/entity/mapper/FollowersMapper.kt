package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowersResponse
import com.untilled.roadcapture.data.datasource.api.dto.follower.FollowingsSortByAlbumResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings
import com.untilled.roadcapture.data.entity.paging.FollowingsSortByAlbum

class FollowersMapper {
    fun transformToFollowers(response: PageResponse<FollowersResponse>): Followers {
        return with(response) {
            Followers(
                total = totalPages,
                page = number,
                followers = content.map {
                    Followers.Follower(
                        0,
                        it.id,
                        it.profileImageUrl,
                        it.username,
                        it.followed,
                    )
                }
            )
        }
    }

    fun transformToFollowings(response: PageResponse<UsersResponse>): Followings {
        return with(response) {
            Followings(
                total = totalPages,
                page = number,
                followings = content.map {
                    Followings.Following(
                        0,
                        it.id,
                        it.profileImageUrl,
                        it.username,
                    )
                }
            )
        }
    }

    fun transformToFollowingsSortByAlbum(response: PageResponse<FollowingsSortByAlbumResponse>): FollowingsSortByAlbum {
        return with(response) {
            FollowingsSortByAlbum(
                total = totalPages,
                page = number,
                followingsSortByAlbum = content.map {
                    FollowingsSortByAlbum.FollowingSortByAlbum(
                        0,
                        it.id,
                        it.profileImageUrl,
                        it.username,
                        it.latestAlbumCreatedAt,
                        it.latestAlbumLastModifiedAt,
                    )
                }
            )
        }
    }


}