package com.untilled.roadcapture.features.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentSettingsBinding
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToAccountSetting
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
                binding.textSettingsLocation.text = "앱 사용 중에만 허용"
            }
            else -> {
                binding.textSettingsLocation.text = "거부"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setOnClickListeners(){
        binding.textSettingsLabelAccount.setOnClickListener {
            navigateToAccountSetting()
        }
        binding.imgSettingsBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
        binding.textSettingsLabelOpinion.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.type = "plain/text"
            val address = arrayOf("kwangddang12@naver.com")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            email.putExtra(Intent.EXTRA_SUBJECT, "로드캡처 건의사항")
            email.putExtra(Intent.EXTRA_TEXT, "이 메일은 개발자에게 전송됩니다.")
            startActivity(email)
        }
        binding.textSettingsLabelLocation.setOnClickListener {
            PermissionLocationBottomSheetDialog().show(
                childFragmentManager,
                DialogTagConstant.PERMISSION_LOCATION_BOTTOM_SHEET
            )
        }
    }
}