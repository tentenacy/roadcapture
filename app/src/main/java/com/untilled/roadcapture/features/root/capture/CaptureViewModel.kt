package com.untilled.roadcapture.features.root.capture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel
@Inject constructor(private val repository: PictureRepository) : ViewModel() {
    private var _pictureList = MutableLiveData<MutableList<Picture>>()
    val pictureList : LiveData<MutableList<Picture>> get() = _pictureList

    init {
        getPictures()
    }

    fun getPictures() {
        viewModelScope.launch {
            repository.getPictures().let { pictures ->
                _pictureList.value = pictures.toMutableList()
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
