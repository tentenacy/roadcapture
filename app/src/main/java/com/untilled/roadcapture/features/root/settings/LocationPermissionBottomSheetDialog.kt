package com.untilled.roadcapture.features.root.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.ModalBottomSheetLocationPermissionBinding
import com.untilled.roadcapture.features.root.RootFragment

class LocationPermissionBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetLocationPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetLocationPermissionBinding.inflate(inflater, container, false)

        setOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.radioGroupLocationPermission.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.radiobutton_location_permission_always -> {
                    (parentFragment as SettingsFragment).binding.textviewSettingServiceLocationDetail.text =
                        "항상 허용"
                    dismiss()
                }
                R.id.radiobutton_location_permission_using-> {
                    (parentFragment as SettingsFragment).binding.textviewSettingServiceLocationDetail.text =
                        "앱 사용중에만 허용"
                    dismiss()
                }
                R.id.radiobutton_location_permission_confirm-> {
                    (parentFragment as SettingsFragment).binding.textviewSettingServiceLocationDetail.text =
                        "항상 확인"
                    dismiss()
                }
                R.id.radiobutton_location_permission_deny-> {
                    (parentFragment as SettingsFragment).binding.textviewSettingServiceLocationDetail.text =
                        "거부"
                    dismiss()
                }
            }
        }
    }

}