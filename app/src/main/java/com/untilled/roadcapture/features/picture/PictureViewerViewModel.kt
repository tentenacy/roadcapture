package com.untilled.roadcapture.features.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumResponse
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PictureViewerViewModel
@Inject constructor(private val albumRepository: AlbumRepository) : BaseViewModel() {
    private val _album = MutableLiveData<AlbumResponse>()
    val album: LiveData<AlbumResponse> get() = _album
    var currentPosition: Int = 0
    fun getAlbumDetail(id: Long) {

        albumRepository.getAlbumDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _album.postValue(response)
            },{

            }).addTo(compositeDisposable)
    }

/*
    private fun getPictureCommentsResultStream(pictureId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PictureCommentsPagingSource(repository,pictureId) }
        ).flow
    }
*/

/*
    fun getPictureComments(pictureId: Int): Flow<PagingData<Comments>> {
        return getPictureCommentsResultStream(pictureId).cachedIn(viewModelScope)
    }
*/

/*
    private fun getAlbumCommentsResultStream(albumId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(repository,albumId) }
        ).flow
    }
*/

/*
    fun getAlbumComments(albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(albumId).cachedIn(viewModelScope)
    }
*/
}

