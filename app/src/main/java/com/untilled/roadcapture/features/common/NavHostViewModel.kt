package com.untilled.roadcapture.features.common

import android.app.Application
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.datasource.api.dto.picture.PictureCreateRequest
import com.untilled.roadcapture.data.datasource.api.ext.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.repository.album.AlbumRepository
import com.untilled.roadcapture.data.repository.picture.PictureRemoteRepository
import com.untilled.roadcapture.data.repository.place.SearchPlaceRepository
import com.untilled.roadcapture.features.base.BaseViewModel
import com.untilled.roadcapture.utils.FAILURE
import com.untilled.roadcapture.utils.SUCCESS
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
class NavHostViewModel //: BaseViewModel() {
@Inject constructor(
    private val application: Application,
    private val remoteRepository: PictureRemoteRepository,
    private val albumRepository: AlbumRepository,
    private val searchPlaceRepository: SearchPlaceRepository
) : BaseViewModel() {

    var pictureList: MutableList<Picture> = mutableListOf()

    var address : TmapAddressInfoResponse? = null
    var addressState = MutableLiveData<Int>()

    var postAlbumState = MutableLiveData<Int>()

    fun insertPicture(picture: Picture) {
        if(picture.thumbnail) {
            pictureList.forEach { it.thumbnail = false }
        }
        pictureList.add(
            picture.apply {
                order = pictureList.size + 1
            }
        )
    }

    fun updatePicture(picture: Picture) {
        if(picture.thumbnail) {
            pictureList.forEach {
                if(it.order != picture.order){
                    it.thumbnail = false
                }
            }
        }
        pictureList[picture.order - 1] = picture
    }

    fun deletePicture(idx : Int) {
        pictureList.removeAt(idx)
        pictureList.forEachIndexed { index, picture ->
            picture.order = index + 1
        }
    }

    fun postAlbum(request: AlbumCreateRequest) {
        request.pictures = pictureList.map { picture ->
            picture.fileUri = FileUtils.getPath(application.applicationContext, picture.fileUri?.toUri())
            picture.toPictureCreateRequest()
        }
        albumRepository.postAlbum(makeImagesParts(request.pictures!!), Json.encodeToString(request))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ v ->
                postAlbumState.postValue(SUCCESS)
                pictureList.clear()
                // todo: Room 에 있는 내용 삭제
            }, { t ->
                postAlbumState.postValue(FAILURE)
            }).addTo(compositeDisposable)
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

    fun getReverseGeoCode(lat: Double, lon: Double) {
        searchPlaceRepository.getReverseGeoCode(lat.toString(), lon.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { addressInfoResponse ->
                addressState.postValue(SUCCESS)
                address = addressInfoResponse
            }, {t->

            })
    }
}