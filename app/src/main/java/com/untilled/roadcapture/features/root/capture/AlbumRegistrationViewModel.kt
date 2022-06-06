package com.untilled.roadcapture.features.root.capture

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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
            .flatMap { pictures ->
                request.pictures = pictures.map { picture ->
                    picture.fileUri =
                        FileUtils.getPath(application.applicationContext, picture.fileUri?.toUri())
                    picture.toPictureCreateRequest()
                }
                albumRepository.postAlbum(
                    makeImagesParts(request.pictures!!),
                    Json.encodeToString(request)
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { v ->
                isComplete.postValue(true)
                deleteCache(application.applicationContext)   // 캐시 디렉토리에 있는 사진들 제거
                deleteAll()   // Room 에 저장된 picture 모두 제거
            }.addTo(compositeDisposable)
    }

    private fun makeImagesParts(pictures: List<PictureCreateRequest>): List<MultipartBody.Part> =
        pictures.map { picture ->
            //RequestBody.create -> deprecated
            MultipartBody.Part.createFormData(
                "images[${picture.order}]",
                picture.path,
                File(picture.path!!).asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
        }

    private fun deleteAll() {
        remoteRepository.deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(compositeDisposable)
    }
}