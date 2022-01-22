package com.untilled.roadcapture.features.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSettingsAccountBinding
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsAccountFragment : Fragment() {
    private var _binding: FragmentSettingsAccountBinding? = null
    private val binding get() = _binding!!

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
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.imageSettingaccountBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textSettingaccountLabelUsername.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountSettingFragment_to_usernameSettingFragment)
        }
        binding.textSettingaccountLabelPwd.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountSettingFragment_to_passwordSettingFragment)
        }
        binding.textSettingaccountLabelServicewithdrawal.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountSettingFragment_to_serviceWithdrawalFragment)
        }
        binding.textSettingaccountLabelLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dlg_logout, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlglogout_confirm)?.setOnClickListener {
            mainActivity().viewModel.logout()
            findNavController().navigate(SettingsAccountFragmentDirections.actionGlobalLoginFragment())
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.text_dlglogout_cancel)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}