package com.untilled.roadcapture.data.datasource.dao

import androidx.room.*
import com.untilled.roadcapture.data.entity.Picture
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface PictureDao {
    @Insert
    fun insertPicture(picture: Picture): Completable

    @Update
    fun updatePicture(picture: Picture): Completable

    @Delete
    fun deletePicture(picture: Picture): Completable

    @Query("select * from picture")
    fun getPictures(): Flowable<List<Picture>>

    @Query("delete from picture")
    fun deleteAll() : Completable
}