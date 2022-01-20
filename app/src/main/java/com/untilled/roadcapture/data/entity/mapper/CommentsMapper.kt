package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums

class CommentsMapper {

    fun transform(response: PageResponse<CommentsResponse>): AlbumComments {
        return with(response) {
            AlbumComments(
                total = totalPages,
                page = number,
                albumComments = content.map {
                    AlbumComments.AlbumComment(
                        0,
                        it.id,
                        it.pictureId,
                        it.createdAt,
                        it.lastModifiedAt,
                        it.content,
                        it.user,
                    )
                }
            )
        }
    }
}