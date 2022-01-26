package com.untilled.roadcapture.features.root.capture

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.datasource.api.dto.address.Address
import com.untilled.roadcapture.data.datasource.api.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.datasource.api.dto.place.PlaceCreateRequest
import com.untilled.roadcapture.data.entity.LocationLatLng
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToCapture
import com.untilled.roadcapture.utils.navigateToSearchPlace
import com.untilled.roadcapture.utils.showPictureDeleteAskingDialog
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    val binding get() = _binding!!
    private var picture: Picture? = null
    private var mode = POST
    private lateinit var locationLatLng: LocationLatLng
    private lateinit var locationManager: LocationManager

    private val viewModel: PictureEditorViewModel by viewModels()

    private val locationListener = LocationListener { location ->
        location
        locationLatLng = LocationLatLng(location.latitude.toFloat(), location.longitude.toFloat())
        viewModel.getReverseGeoCode(location.latitude.toFloat(), location.longitude.toFloat())
    }

    private val orderObserver: (Long) -> Unit = { order ->
        picture?.order = order
    }

    private val placeOnClickListener: (View?) -> Unit = {
        navigateToSearchPlace(makePicture())
    }

    private val addressObserver: (TmapAddressInfoResponse) -> Unit = { addressInfoResponse ->
        if (picture?.place == null) {
            picture?.place = addressToPlace(addressInfoResponse)
            binding.textPictureEditorPlace.text = picture?.place?.name
        }
    }


    private val checkOnClickListener: (View?) -> Unit = {
        if (picture?.place == null) {
            Toast.makeText(requireContext(), "장소를 등록해주세요", Toast.LENGTH_SHORT).show()
        } else {
            if (mode == POST) {
                viewModel.insertPicture(makePicture())
            } else if (mode == EDIT) {
                viewModel.updatePicture(makePicture())
            }
            navigateToCapture()
        }
    }

    private val confirmOnClickListener: () -> Unit = {
        if (mode == EDIT) {
            viewModel.deletePicture(makePicture())
        }
        navigateToCapture()
    }

    private val deleteOnClickListener: (View?) -> Unit = {
        showPictureDeleteAskingDialog(confirmOnClickListener)
    }

    private fun addressToPlace(tmapAddress: TmapAddressInfoResponse): PlaceCreateRequest {
        val addressList: List<String?> = tmapAddress.tmapAddressInfo.fullAddress!!.split(",")
        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")).toString()
        return PlaceCreateRequest(
            placeCreatedAt = now,
            placeLastModifiedAt = now,
            latitude = locationLatLng.latitude.toDouble(),
            longitude = locationLatLng.longitude.toDouble(),
            name = addressList[0]!!,
            Address(
                addressName = addressList[1] ?: addressList[0]!!,
                roadAddressName = addressList[2] ?: addressList[0]!!,
                region1DepthName = tmapAddress.tmapAddressInfo.cityDo ?: "",
                region2DepthName = tmapAddress.tmapAddressInfo.guGun ?: "",
                region3DepthName = tmapAddress.tmapAddressInfo.adminDong ?: tmapAddress.tmapAddressInfo.legalDong
                ?: tmapAddress.tmapAddressInfo.eupMyun ?: "",
                zoneNo = ""
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        getNavArgs()
        initLocationManager()
        getLocation()

        return binding.root
    }

    private fun initLocationManager() {
        if (::locationManager.isInitialized.not()) {
            locationManager =
                mainActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    private fun getLocation() {
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                with(locationManager) {
                    requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0, 100f, locationListener
                    )
                    requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0, 100f, locationListener
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.order.observe(viewLifecycleOwner, orderObserver)
        viewModel.addressInfoResponse.observe(viewLifecycleOwner, addressObserver)
    }

    private fun setOnClickListeners() {
        binding.imgPictureEditorBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.textPictureEditorPlace.setOnClickListener(placeOnClickListener)
        binding.imgPictureEditorCheck.setOnClickListener(checkOnClickListener)
        binding.imgPictureEditorDelete.setOnClickListener(deleteOnClickListener)
        binding.checkboxPictureEditorThumbnail.setOnCheckedChangeListener { _, isChecked ->
            picture?.thumbnail = isChecked
        }
    }

    private fun makePicture(): Picture = picture!!.apply {
        description = binding.edtPictureEditorDesc.text.toString()
    }

    private fun getNavArgs() {
        val args: PictureEditorFragmentArgs by navArgs()
        if (args.picture != null) {
            mode = if (args.picture!!.id == 0L) POST else EDIT
            if (args.picture!!.order == 0L) {
                viewModel.getNextOrder()
            }

            picture = args.picture
            binding.picture = picture
        }
    }

    companion object {
        private const val POST = 100
        private const val EDIT = 101
    }
}