package com.untilled.roadcapture.data.repository.picture

import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.entity.Picture
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRepositoryImpl
@Inject
constructor(
        private val pictureDao: PictureDao
    ): PictureRepository {
    override suspend fun insertPicture(picture: Picture) {
        pictureDao.insertPicture(picture)
    }

    override suspend fun updatePicture(picture: Picture) {
        pictureDao.updatePicture(picture)
    }

    override suspend fun deletePicture(picture: Picture) {
        pictureDao.deletePicture(picture)
    }

    override suspend fun getPictures() : List<Picture> = pictureDao.getPictures()

    override suspend fun deleteAll() {
        pictureDao.deleteAll()
    }
}