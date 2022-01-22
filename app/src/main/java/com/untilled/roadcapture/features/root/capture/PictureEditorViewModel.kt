package com.untilled.roadcapture.features.root.capture

import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRemoteRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PictureEditorViewModel
@Inject constructor(private val remoteRepository: PictureRemoteRepository) : BaseViewModel() {

    fun insertPicture(picture: Picture) {
        val completableList = mutableListOf<Completable>()

        if(picture.thumbnail) {     // 썸네일 체크 되어있다면 기존 썸네일 초기화 진행 후 삽입
            completableList.add(remoteRepository.initThumbnail())
        }
        completableList.add(remoteRepository.insertPicture(picture))

        Completable.concat(completableList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun updatePicture(picture: Picture) {
        val completableList = mutableListOf<Completable>()

        if(picture.thumbnail) {     // 썸네일 체크 되어있다면 기존 썸네일 초기화 진행 후 삽입
            completableList.add(remoteRepository.initThumbnail())
        }
        completableList.add(remoteRepository.updatePicture(picture))

        Completable.concat(completableList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun deletePicture(picture: Picture) {
        remoteRepository.deletePicture(picture)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}