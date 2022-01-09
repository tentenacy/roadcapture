package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentForgotpwdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotpwdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotpwdBinding.inflate(layoutInflater,container,false)
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
        binding.imgForgotpwdBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}