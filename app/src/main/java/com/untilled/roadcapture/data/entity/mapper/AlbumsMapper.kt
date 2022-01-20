package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.paging.Albums

class AlbumsMapper {

    fun transform(response: PageResponse<AlbumsResponse>): Albums {
        return with(response) {
            Albums(
                total = totalPages,
                page = number,
                albums = content.map {
                    Albums.Album(
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