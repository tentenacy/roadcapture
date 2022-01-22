package com.untilled.roadcapture.features.root.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRemoteRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel
@Inject constructor(private val remoteRepository: PictureRemoteRepository) : BaseViewModel() {
    private var _pictureList = MutableLiveData<MutableList<Picture>>()
    val pictureList: LiveData<MutableList<Picture>> get() = _pictureList

    init {
        getPictures()
    }

    fun getPictures() {
        remoteRepository.getPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pictures ->
                _pictureList.value = pictures.toMutableList()
            }.addTo(compositeDisposable)
    }

    fun deleteAll() {
        remoteRepository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}
