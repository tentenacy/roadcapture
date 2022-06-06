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
import com.untilled.roadcapture.data.entity.LocationLatLng
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.features.common.NavHostViewModel
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.permission.checkPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    val binding get() = _binding!!

    private val navHostViewModel: NavHostViewModel by viewModels({ requireParentFragment() })
    private lateinit var locationManager: LocationManager
    private var locationLatLng: LocationLatLng? = null
    private var picture: Picture? = null
    private var mode = POST

    private val locationListener = LocationListener { location ->
        //navHostViewModel.getReverseGeoCode(location.latitude, location.longitude)
        locationLatLng = LocationLatLng(location.latitude, location.longitude)
    }

    private val placeOnClickListener: (View?) -> Unit = {
        showPlaceSearchBottomSheetDialog()
    }

    private fun showPlaceSearchBottomSheetDialog() {
        PlaceSearchBottomSheetDialog().apply {
            searchOnClickListener = {
                navigateToSearchPlace(makePicture())
                dismiss()
            }
        }.show(
            childFragmentManager,
            DialogTagConstant.PLACE_SEARCH_BOTTOM_SHEET
        )
    }

    private val addressObserver: (Int) -> Unit = { state ->
        if (state == SUCCESS) {
            if (picture?.place == null) {
                picture?.place = navHostViewModel.address!!.tmapAddressInfo.toPlace()
                binding.textPictureEditorPlace.text = picture?.place?.name ?: ""

                locationLatLng?.let {
                    picture?.place?.latitude = it.latitude
                    picture?.place?.longitude = it.longitude
                }
//                picture?.place?.latitude = locationLatLng.latitude
//                picture?.place?.longitude = locationLatLng.longitude
            }

            navHostViewModel.addressState.value = DEFAULT
        }
    }

    private val checkOnClickListener: (View?) -> Unit = checkOnClickListener@{
        if (picture?.place == null) {
            Toast.makeText(requireContext(), "장소를 등록해주세요", Toast.LENGTH_SHORT).show()
            return@checkOnClickListener
        }

        if (mode == POST) {
            navHostViewModel.insertPicture(makePicture())
        } else if (mode == EDIT) {
            navHostViewModel.updatePicture(makePicture())
        }
        navigateToCapture()
    }

    private val deleteOnClickListener: (View?) -> Unit = {
        showPictureDeleteAskingDialog(confirmOnClickListener)
    }

    private val confirmOnClickListener: () -> Unit = {
        if (mode == EDIT) {
            navHostViewModel.deletePicture(picture!!.order - 1)
        }
        navigateToCapture()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        getNavArgs()
        setMode()
        initLocationManager()
        getLocation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
    }

    private fun getNavArgs() {
        val args: PictureEditorFragmentArgs by navArgs()
        picture = args.picture
    }

    private fun setMode() {
        mode = if (picture?.order == 0) POST else EDIT
    }

    private fun initLocationManager() {
        if (::locationManager.isInitialized.not()) {
            locationManager =
                mainActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    private fun getLocation() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return
        }
        if (!checkPermissions(
                listOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        ) {
            return
        }

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

    private fun initView() {
        binding.picture = picture
    }

    private fun observeData() {
        navHostViewModel.addressState.observe(viewLifecycleOwner, addressObserver)
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

    companion object {
        private const val POST = 100
        private const val EDIT = 101
    }
}