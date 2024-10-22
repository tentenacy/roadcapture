package com.untilled.roadcapture.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.data.datasource.sharedpref.User
import com.untilled.roadcapture.databinding.FragmentSettingsAccountBinding
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsAccountFragment : Fragment() {
    private var _binding: FragmentSettingsAccountBinding? = null
    val binding get() = _binding!!

    private val logoutConfirmListener: () -> Unit = {
        mainActivity().viewModel.logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsAccountBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = User
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.imageSettingaccountBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
        binding.textSettingaccountLabelUsername.setOnClickListener {
            navigateToUsernameSetting()
        }
        binding.textSettingaccountLabelPwd.setOnClickListener {
            navigateToPasswordSetting()
        }
        binding.textSettingaccountLabelServicewithdrawal.setOnClickListener {
            navigateToServiceWithdrawal()
        }
        binding.textSettingaccountLabelLogout.setOnClickListener {
            showLogoutConfirmationDialog(logoutConfirmListener)
        }
    }
}