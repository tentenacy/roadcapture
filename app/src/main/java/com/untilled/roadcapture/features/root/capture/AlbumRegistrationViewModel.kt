package com.untilled.roadcapture.features.root.capture

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.util.FileUtil
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.picture.PictureRemoteRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.deleteCache
import com.yalantis.ucrop.util.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AlbumRegistrationViewModel
@Inject constructor(
    private val application: Application,
    private val remoteRepository: PictureRemoteRepository,
    private val albumRepository: AlbumRepository
) : BaseViewModel() {
    val isComplete = MutableLiveData<Boolean>()

    fun getPictures(request: AlbumCreateRequest) {
        remoteRepository.getPictures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { pictures ->
                request.pictures = pictures.map { picture ->
                    picture.fileUri = FileUtils.getPath(application.applicationContext, picture.fileUri?.toUri())
                    picture.toPictureCreateRequest()
                }
                Pair(
                    makeImageRequest(request.pictures!!),
                    Json.encodeToString(request)
                )
            }
            .subscribe { pair ->
                postAlbum(pair.first!!, pair.second)
            }.addTo(compositeDisposable)
    }

    private fun makeImageRequest(pictures: List<PictureCreateRequest>) : List<MultipartBody.Part> =
        pictures?.map { picture ->
            val file = File(picture.path)
            val requestFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull()) // 기존 RequestBody.create 는 deprecated 됨
            MultipartBody.Part.createFormData("images[${picture.order}]", picture.path, requestFile)
        }

    private fun postAlbum(images: List<MultipartBody.Part>, data: String) {
        albumRepository.postAlbum(images, data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                isComplete.postValue(true)
                deleteCache(application.applicationContext)   // 캐시 디렉토리에 있는 사진들 제거
                deleteAll()   // Room 에 저장된 picture 모두 제거
            }, { t->
                isComplete.postValue(false)
            }).addTo(compositeDisposable)
    }

    private fun deleteAll() {
        remoteRepository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}