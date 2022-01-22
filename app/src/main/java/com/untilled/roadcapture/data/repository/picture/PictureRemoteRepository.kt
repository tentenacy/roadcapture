package com.untilled.roadcapture.data.repository.picture

import com.untilled.roadcapture.data.entity.Picture
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface PictureRemoteRepository {
    fun insertPicture(picture: Picture) : Completable

    fun updatePicture(picture: Picture) : Completable

    fun deletePicture(picture: Picture) : Completable

    fun getPictures() : Flowable<List<Picture>>

    fun deleteAll() : Completable

    fun initThumbnail() : Completable

    fun getNextOrder() : Single<Long>
}