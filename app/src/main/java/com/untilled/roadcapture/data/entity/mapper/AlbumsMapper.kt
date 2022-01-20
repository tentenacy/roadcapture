package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.AlbumsPage
import java.util.*

class AlbumsMapper {

    fun transform(response: PageResponse<AlbumsResponse>): AlbumsPage {
        return with(response) {
            AlbumsPage(
                total = totalPages,
                page = number,
                albums = content.map {
                    AlbumsPage.Albums(
                        0,
                        it.id,
                        it.createdAt,
                        it.lastModifiedAt,
                        it.title,
                        it.description,
                        it.thumbnailUrl,
                        it.user,
                        it.viewCount,
                        it.likeCount,
                        it.commentCount,
                        it.liked,
                    )
                }
            )
        }
    }
}