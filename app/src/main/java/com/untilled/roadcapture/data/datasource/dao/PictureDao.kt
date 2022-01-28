package com.untilled.roadcapture.data.datasource.dao

import androidx.room.*
import com.untilled.roadcapture.data.entity.Picture
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface PictureDao {
    @Insert
    fun insertPicture(picture: Picture): Completable

    @Update
    fun updatePicture(picture: Picture): Completable

    @Delete
    fun deletePicture(picture: Picture): Completable

    @Query("SELECT * FROM picture ORDER BY `order`")
    fun getPictures(): Flowable<List<Picture>>

    @Query("DELETE FROM picture")
    fun deleteAll() : Completable

    @Query("UPDATE picture SET thumbnail = 0 WHERE thumbnail = 1")
    fun initThumbnail() : Completable

    @Query("SELECT IFNULL(MAX(`order`), 0) FROM picture")
    fun getNextOrder(): Single<Int>
}