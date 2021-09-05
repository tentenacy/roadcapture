package com.untilled.roadcapture.features.root.capture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.untilled.roadcapture.data.entity.SearchResult

class PictureEditorViewModel : ViewModel() {
    val imageUri = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val searchResult : MutableLiveData <SearchResult>? = MutableLiveData<SearchResult>()
    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    fun initProperty() {
        imageUri.value = ""
        date.value = ""
        searchResult?.value = null
        name.value = ""
        description.value = ""
    }
}