package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentTermsOfServiceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsOfServiceFragment : Fragment() {
    private var _binding: FragmentTermsOfServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTermsOfServiceBinding.inflate(layoutInflater,container,false)

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
        binding.imageviewTermsOfServiceBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}