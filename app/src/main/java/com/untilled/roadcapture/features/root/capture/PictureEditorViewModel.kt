package com.untilled.roadcapture.features.root.capture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.picture.PictureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureEditorViewModel
@Inject constructor(private val repository: PictureRepository) : ViewModel() {

    fun insertPicture(picture: Picture) {
        viewModelScope.launch {
            repository.insertPicture(picture)
        }
    }
}