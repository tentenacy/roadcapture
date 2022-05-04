package com.untilled.roadcapture.features.root.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val albumPagingRepository: AlbumPagingRepository
): BaseViewModel() {

    val search = MutableLiveData("")
    var itemCount = MutableLiveData<Int>()

    private var _albums = MutableLiveData<PagingData<Albums.Album>>()
    val album: LiveData<PagingData<Albums.Album>> get() = _albums

    fun getAlbums(cond: AlbumsCondition){
        albumPagingRepository.albums(cond)
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _albums.value = pagingData
            },{

            }).addTo(compositeDisposable)
    }
}