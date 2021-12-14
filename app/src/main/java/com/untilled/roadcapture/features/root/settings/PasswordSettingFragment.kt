package com.untilled.roadcapture.features.root.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentSettingPasswordBinding
import com.untilled.roadcapture.databinding.FragmentSettingUsernameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordSettingFragment: Fragment() {
    private var _binding: FragmentSettingPasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingPasswordBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.imageviewSettingPasswordBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}