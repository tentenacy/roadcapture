package com.untilled.roadcapture.features.root.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.data.datasource.api.dto.album.Albums
import com.untilled.roadcapture.data.datasource.api.dto.comment.Comments
import com.untilled.roadcapture.data.datasource.api.dto.user.UserFollowResponse
import com.untilled.roadcapture.data.repository.album.AlbumCommentsPagingSource
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.album.AlbumsPagingSource
import com.untilled.roadcapture.data.repository.user.UserRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AlbumsViewModel
@Inject constructor(
    private val albumRepository: AlbumRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var currentDateTimeFrom: String? = null
    private var currentDateTimeTo: String? = null
    private var currentAlbumsResult: Flow<PagingData<Albums>>? = null

    private val _user =  MutableLiveData<UserFollowResponse>()
    val user: LiveData<UserFollowResponse> get() = _user

    fun getAlbums(token: String,dateTimeFrom: String?, dateTimeTo: String?): Flow<PagingData<Albums>>{
        val lastResult = currentAlbumsResult
        if(dateTimeFrom == currentDateTimeFrom && dateTimeTo == currentDateTimeTo && lastResult != null){
            return lastResult
        }
        currentDateTimeFrom = dateTimeFrom
        currentDateTimeTo = dateTimeTo
        val newResult: Flow<PagingData<Albums>> = getAlbumsResultStream(token,dateTimeFrom,dateTimeTo).cachedIn(viewModelScope)
        currentAlbumsResult = newResult
        return newResult
    }

    fun getAlbumComments(token: String,albumId: Int): Flow<PagingData<Comments>> {
        return getAlbumCommentsResultStream(token,albumId).cachedIn(viewModelScope)
    }

    private fun getAlbumsResultStream(token: String,dateTimeFrom: String?, dateTimeTo: String?): Flow<PagingData<Albums>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumsPagingSource(albumRepository,token,dateTimeFrom,dateTimeTo) }
        )
            .flow
    }

    private fun getAlbumCommentsResultStream(token: String,albumId: Int): Flow<PagingData<Comments>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { AlbumCommentsPagingSource(albumRepository,token,albumId) }
        ).flow
    }

    fun getUserFollowing(id: Int, token: String, page: Int? = null, size: Int? = null, sort: String? = null,username: String? = null){
        userRepository.getUserFollowing(id, token, page, size, sort, username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _user.postValue(user)
            },{ error ->
                Logger.d("test: $error")
            })
    }

}