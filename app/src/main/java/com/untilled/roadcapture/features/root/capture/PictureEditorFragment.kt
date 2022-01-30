package com.untilled.roadcapture.features.root.capture

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.datasource.api.ext.dto.address.TmapAddressInfoResponse
import com.untilled.roadcapture.data.entity.LocationLatLng
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    val binding get() = _binding!!
    private var picture: Picture? = null
    private var mode = POST
    private lateinit var locationManager: LocationManager
    private lateinit var locationLatLng: LocationLatLng

    private val viewModel: PictureEditorViewModel by viewModels()

    private val locationListener = LocationListener { location ->
        viewModel.getReverseGeoCode(location.latitude, location.longitude)
        locationLatLng = LocationLatLng(location.latitude, location.longitude)
    }

    private val orderObserver: (Int) -> Unit = { order ->
        picture?.order = order
    }

    private val placeOnClickListener: (View?) -> Unit = {
        navigateToSearchPlace(makePicture())
    }

    private val addressObserver: (TmapAddressInfoResponse) -> Unit = { addressInfoResponse ->
        if (picture?.place == null) {
            picture?.place = addressInfoResponse.tmapAddressInfo.toPlace()
            picture?.place?.latitude = locationLatLng.latitude
            picture?.place?.longitude = locationLatLng.longitude
            binding.textPictureEditorPlace.text = picture?.place?.name ?: ""
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        getNavArgs()
        setMode()
        setOrder()
        initLocationManager()
        getLocation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initLocationManager() {
        if (::locationManager.isInitialized.not()) {
            locationManager =
                mainActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    private fun getLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requireContext().checkSelfPermission(
                listOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
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

        picture = args.picture
        binding.picture = picture
    }

    private fun setMode() {
        mode = if(picture?.id == 0L) POST else EDIT
    }

    private fun setOrder() {
        if (picture?.order == 0) {
            viewModel.getNextOrder()
        }
    }

    companion object {
        private const val POST = 100
        private const val EDIT = 101
    }
}