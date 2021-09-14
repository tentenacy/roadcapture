package com.untilled.roadcapture.features.root.capture

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.PictureThumbnail
import com.untilled.roadcapture.data.repository.PictureThumbnailDB
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.File
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class CaptureFragment : Fragment(), OnMapReadyCallback, CoroutineScope {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private lateinit var pictureThumbnailList: List<PictureThumbnail>

    private lateinit var db: PictureThumbnailDB
    private var naverMap: NaverMap? = null

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    // 갤러리 사진 가져오는 intent 콜백 등록
    private val getContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            imageUri = it.data?.data
            Navigation.findNavController(binding.root)
                .navigate(CaptureFragmentDirections.actionCaptureFragmentToCropFragment(imageUri.toString()))
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
            .setType(MediaStore.Images.Media.CONTENT_TYPE)
            .setType("image/*")

        getContent.launch(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)

        job = Job()
        initDb()
        initNaverMap()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.fabCaptureCapture.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_captureFragment_to_cameraFragment)
        }
        binding.imageviewCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabCapturePickerGallery.setOnClickListener {
            pickFromGallery()
        }
        binding.fabCaptureRecord.setOnClickListener {
            launch(coroutineContext) {
                try {
                    withContext(Dispatchers.IO) {
                        db.infoWindowDao().deleteAll()
                        deleteCache(requireContext())
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    private fun deleteCache(context: Context) {
        try {
            val dir: File = context.cacheDir
            deleteDir(dir)
        } catch (e: java.lang.Exception) {
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }

    private fun initDb() {
        db = Room.databaseBuilder(
            requireContext(),
            PictureThumbnailDB::class.java,
            "InfoWindowDatabase"
        ).build()
    }

    private fun getNavArgs() {
        val args: CaptureFragmentArgs by navArgs()
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    if (args.picture != null) {
                        db.infoWindowDao()
                            .insertInfoWindow(
                                PictureThumbnail(
                                    uid = null,
                                    picture = args.picture!!
                                )
                            )
                    }
                    pictureThumbnailList = db.infoWindowDao().getAll()

                    withContext(Dispatchers.Main) {
                        showPictureThumbnails()
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun initNaverMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.fragment_map) as? MapFragment
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fragment_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun showPictureThumbnails() {
        for (pictureThumbnail in pictureThumbnailList) {
            if (pictureThumbnail.picture != null) {
                val infoWindow = InfoWindow()
                infoWindow.apply {
                    adapter = CaptureInfoWindowAdapter(
                        requireContext(), binding.root as ViewGroup,
                        pictureThumbnail.picture.imageUri!!
                    )
                    position = LatLng(
                        pictureThumbnail.picture.searchResult?.locationLatLng?.latitude?.toDouble()
                            ?: 37.5670135,
                        pictureThumbnail.picture.searchResult?.locationLatLng?.longitude?.toDouble()
                            ?: 126.9783740
                    )
                }.open(naverMap!!)
            }
        }
        naverMap?.moveCamera(
            CameraUpdate.scrollTo(
                LatLng(
                    pictureThumbnailList.last().picture?.searchResult?.locationLatLng?.latitude?.toDouble()
                        ?: 37.5670135,
                    pictureThumbnailList.last().picture?.searchResult?.locationLatLng?.longitude?.toDouble()
                        ?: 126.9783740
                )
            )
        )
    }

    override fun onMapReady(_naverMap: NaverMap) {
        _naverMap.isLiteModeEnabled = true
        naverMap = _naverMap

        getNavArgs()
    }

}