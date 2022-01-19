package com.untilled.roadcapture.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentSettingsPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsPasswordFragment: Fragment() {
    private var _binding: FragmentSettingsPasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsPasswordBinding.inflate(inflater,container,false)

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
        binding.imageSettingpwdBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}