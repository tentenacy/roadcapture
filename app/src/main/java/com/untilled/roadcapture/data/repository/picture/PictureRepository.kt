package com.untilled.roadcapture.data.repository.picture

import com.untilled.roadcapture.data.entity.Picture

interface PictureRepository {
    suspend fun insertPicture(picture: Picture)

    suspend fun updatePicture(picture: Picture)

    suspend fun deletePicture(picture: Picture)

    suspend fun getPictures() : List<Picture>

    suspend fun deleteAll()
}