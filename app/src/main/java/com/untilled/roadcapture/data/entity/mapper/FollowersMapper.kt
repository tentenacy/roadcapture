package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Followers

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
}