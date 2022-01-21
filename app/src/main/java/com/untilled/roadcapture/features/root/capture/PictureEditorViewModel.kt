package com.untilled.roadcapture.features.root.capture

import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PictureEditorViewModel
@Inject constructor(private val repository: PictureRepository) : BaseViewModel() {

    fun insertPicture(picture: Picture) {
        repository.insertPicture(picture)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun updatePicture(picture: Picture) {
        repository.updatePicture(picture)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun deletePicture(picture: Picture) {
        repository.deletePicture(picture)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}