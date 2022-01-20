package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.comment.CommentsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.paging.AlbumComments
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.PictureComments

class CommentsMapper {

    fun transformToAlbumComments(response: PageResponse<CommentsResponse>): AlbumComments {
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

    fun transformToPictureComments(response: PageResponse<CommentsResponse>): PictureComments {
        return with(response) {
            PictureComments(
                total = totalPages,
                page = number,
                pictureComments = content.map {
                    PictureComments.PictureComment(
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