package com.untilled.roadcapture.features.root.capture

import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.picture.PictureRemoteRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AlbumRegistrationViewModel
@Inject constructor(
    private val remoteRepository: PictureRemoteRepository,
    private val albumRepository: AlbumRepository
) : BaseViewModel() {
    private var pictureCreateRequestList = mutableListOf<PictureCreateRequest>()

    fun postAlbum(title: String, description: String?) {
            AlbumCreateRequest(
                title = title,
                description = description,
                pictures = pictureCreateRequestList
            )
    }

    fun getPictures() {
        remoteRepository.getPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pictures ->
                getPictureCreateRequestList(pictures)
            }.addTo(compositeDisposable)
    }

    private fun getPictureCreateRequestList(pictures: List<Picture>){
        for (picture in pictures) {
            pictureCreateRequestList.add(picture.toPictureCreateRequest())
        }
    }
}