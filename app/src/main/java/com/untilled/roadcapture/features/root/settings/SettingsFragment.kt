package com.untilled.roadcapture.features.root.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)

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
                binding.textSettingLocation.text = "앱 사용 중에만 허용"
            }
            else -> {
                binding.textSettingLocation.text = "거부"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setOnClickListeners(){
        binding.textSettingLabelAccount.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_settingsFragment_to_accountSettingFragment)
        }
        binding.imageSettingBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textSettingLabelOpinion.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("kwangddang12@naver.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "로드캡처 건의사항")
            email.putExtra(Intent.EXTRA_TEXT, "이 메일은 개발자에게 전송됩니다.")
            startActivity(email)
        }
        binding.textSettingLabelLocation.setOnClickListener {
            val locationPermissionBottomSheetDialog = PermissionLocationBottomSheetDialog()
            locationPermissionBottomSheetDialog.show(childFragmentManager, "locationPermissionBottomSheet")
        }
    }
}