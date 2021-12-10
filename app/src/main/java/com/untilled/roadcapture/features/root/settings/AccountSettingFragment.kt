package com.untilled.roadcapture.features.root.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSettingAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountSettingFragment : Fragment() {
    private var _binding : FragmentSettingAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingAccountBinding.inflate(inflater,container,false)

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

    private fun setOnClickListeners(){
        binding.imageviewSettingAccountBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textviewSettingAccountUsername.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountSettingFragment_to_usernameSettingFragment)
        }
        binding.textviewSettingAccountPassword.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountSettingFragment_to_passwordSettingFragment)
        }
    }
}