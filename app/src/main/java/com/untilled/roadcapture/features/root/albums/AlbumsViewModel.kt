package com.untilled.roadcapture.features.root.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsCondition
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.StudioUserResponse
import com.untilled.roadcapture.data.entity.paging.Albums
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.paging.AlbumPagingRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.dateFrom
import com.untilled.roadcapture.utils.start
import com.untilled.roadcapture.utils.toDateTimeFormat
import com.untilled.roadcapture.utils.tomorrow
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val albumPagingRepository: AlbumPagingRepository,
    private val albumRepository: AlbumRepository
) : BaseViewModel() {

    companion object {
        const val EVENT_SEARCH = 1000
    }

    private val _durationCheckedRadioId = MutableLiveData<Int?>(R.id.radiobtn_dlgfilter_whole)
    val durationCheckedRadioId: LiveData<Int?> = _durationCheckedRadioId

    private val _sortCheckedRadioId = MutableLiveData<Int?>(R.id.radiobtn_dlgfilter_sort_latest)
    val sortCheckedRadioId: LiveData<Int?> = _sortCheckedRadioId

    private val _dateFromText = MutableLiveData<String?>(null)
    val dateFromText: LiveData<String?> = _dateFromText

    private val _dateToText = MutableLiveData<String?>(null)
    val dateToText: LiveData<String?> = _dateToText

    private val _sortText = MutableLiveData<String?>(null)
    val sortText: LiveData<String?> = _sortText

    private var _albums = MutableLiveData<PagingData<Albums.Album>>()
    val album: LiveData<PagingData<Albums.Album>> get() = _albums

    private val _user = MutableLiveData<PageResponse<StudioUserResponse>>()
    val user: LiveData<PageResponse<StudioUserResponse>> get() = _user

    private val _followingAlbums = MutableLiveData<PageResponse<AlbumsResponse>>()
    val followingAlbums: LiveData<PageResponse<AlbumsResponse>> get() = _followingAlbums

    fun setDurationCheckedRadioId(id: Int?) {
        _durationCheckedRadioId.value = id
    }

    fun setSortCheckedRadioId(id: Int?) {
        _sortCheckedRadioId.value = id
    }

    fun setDateFromText(dateFromText: String?) {
        _dateFromText.value = dateFromText
    }

    fun setDateToText(dateToText: String?) {
        _dateToText.value = dateToText
    }

    fun setSortText(sortText: String?) {
        _sortText.value = sortText
    }

    fun albums() {
        albumPagingRepository.albums(
            AlbumsCondition(
                dateTimeFrom = dateFromText.value?.let { dateFrom(it).start().toDateTimeFormat() },
                dateTimeTo = dateToText.value?.let { dateFrom(it).tomorrow().start().toDateTimeFormat() },
                sort = sortText.value,
            )
        )
            .subscribeOn(AndroidSchedulers.mainThread())
            .cachedIn(viewModelScope)
            .subscribe({ pagingData ->
                _albums.postValue(pagingData)
            }) { t ->
                Logger.e("${t}")
            }.addTo(compositeDisposable)
    }

    fun likeAlbum(albumsId: Long) {
        albumRepository.likeAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }).addTo(compositeDisposable)
    }

    fun unlikeAlbum(albumsId: Long) {
        albumRepository.unlikeAlbum(albumsId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            }).addTo(compositeDisposable)
    }

    fun deleteAlbum(albumId: Long){
        albumRepository.deleteAlbum(albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            }).addTo(compositeDisposable)
    }

}