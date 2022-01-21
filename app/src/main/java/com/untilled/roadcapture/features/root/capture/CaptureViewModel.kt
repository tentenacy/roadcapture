package com.untilled.roadcapture.features.root.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel
@Inject constructor(private val repository: PictureRepository) : BaseViewModel() {
    private var _pictureList = MutableLiveData<MutableList<Picture>>()
    val pictureList: LiveData<MutableList<Picture>> get() = _pictureList

    init {
        getPictures()
    }

    fun getPictures() {
        repository.getPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { pictures ->
                _pictureList.value = pictures.toMutableList()
            }.addTo(compositeDisposable)
    }

    fun deleteAll() {
        repository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}
