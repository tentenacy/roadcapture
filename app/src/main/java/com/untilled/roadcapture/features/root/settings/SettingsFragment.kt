package com.untilled.roadcapture.features.root.settings

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            -> {
                binding.textviewSettingServiceLocationDetail.text = "앱 사용 중에만 허용"
            }
            else -> {
                binding.textviewSettingServiceLocationDetail.text = "거부"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setOnClickListeners(){
        binding.textviewSettingAccount.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_settingsFragment_to_accountSettingFragment)
        }
        binding.imageviewSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textviewSettingSupportOpinion.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("kwangddang12@naver.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "로드캡처 건의사항")
            email.putExtra(Intent.EXTRA_TEXT, "이 메일은 개발자에게 전송됩니다.")
            startActivity(email)
        }
        binding.textviewSettingServiceLocation.setOnClickListener {
            val locationPermissionBottomSheetDialog = LocationPermissionBottomSheetDialog()
            locationPermissionBottomSheetDialog.show(childFragmentManager, "locationPermissionBottomSheet")
        }
    }
}