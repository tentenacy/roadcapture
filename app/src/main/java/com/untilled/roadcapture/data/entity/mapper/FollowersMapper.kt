package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Followers
import com.untilled.roadcapture.data.entity.paging.Followings

class FollowersMapper {
    fun transformToFollowers(response: PageResponse<UsersResponse>): Followers {
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
                        it.introduction,
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
                        it.introduction,
                    )
                }
            )
        }
    }

}