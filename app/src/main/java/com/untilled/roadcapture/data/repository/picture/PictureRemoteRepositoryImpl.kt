package com.untilled.roadcapture.data.repository.picture

import com.untilled.roadcapture.data.datasource.dao.PictureDao
import com.untilled.roadcapture.data.entity.Picture
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PictureRemoteRepositoryImpl
@Inject
constructor(
    private val pictureDao: PictureDao
) : PictureRemoteRepository {
    override fun insertPicture(picture: Picture): Completable =
        pictureDao.insertPicture(picture)


    override fun updatePicture(picture: Picture): Completable =
        pictureDao.updatePicture(picture)


    override fun deletePicture(picture: Picture): Completable =
        pictureDao.deletePicture(picture)


    override fun getPictures(): Flowable<List<Picture>> = pictureDao.getPictures()

    override fun deleteAll(): Completable =
        pictureDao.deleteAll()

    override fun initThumbnail() : Completable =
        pictureDao.initThumbnail()

    override fun getNextOrder() : Single<Long> =
        pictureDao.getNextOrder()
}