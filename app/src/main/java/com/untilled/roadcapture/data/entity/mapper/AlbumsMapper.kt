package com.untilled.roadcapture.data.entity.mapper

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.album.UserAlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.entity.paging.UserAlbums

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
                        it.thumbnailPicture,
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

    fun transform(response: PageResponse<UserAlbumsResponse>): UserAlbums {
        return with(response) {
            UserAlbums(
                total = totalPages,
                page = number,
                userAlbums = content.map {
                    UserAlbums.UserAlbum(
                        0,
                        it.id,
                        it.createdAt,
                        it.lastModifiedAt,
                        it.title,
                        it.thumbnailPicture,
                    )
                }
            )
        }
    }
}