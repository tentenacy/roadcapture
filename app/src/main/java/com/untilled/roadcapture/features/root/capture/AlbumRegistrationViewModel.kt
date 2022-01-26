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
    private lateinit var pictures: List<PictureCreateRequest>

    fun postAlbum(title: String, description: String?) {
            AlbumCreateRequest(
                title = title,
                description = description,
                pictures = pictures
            )
    }

    fun getPictures() {
        remoteRepository.getPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _pictures ->
                pictures = getPictureCreateRequestList(_pictures)
            }.addTo(compositeDisposable)
    }

    private fun getPictureCreateRequestList(_pictures: List<Picture>): List<PictureCreateRequest> {
        val pictures = mutableListOf<PictureCreateRequest>()
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")

        for (i in _pictures.indices) {
            pictures.add(
                PictureCreateRequest(
                    thumbnail = _pictures[i].thumbnail,
                    createdAt = now.plusSeconds(i.toLong()).format(formatter).toString(), // 순서 대로 넣기
                    lastModifiedAt = now.plusSeconds(i.toLong()).format(formatter).toString(),
                    description = _pictures[i].description,
                    place = _pictures[i].place!!,
                    imageUrl = _pictures[i].imageUrl
                )
            )
        }
        return pictures.toList()
    }
}