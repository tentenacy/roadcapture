package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentLoginEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailFragment : Fragment() {
    private var _binding: FragmentLoginEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginEmailBinding.inflate(layoutInflater,container,false)
        (requireActivity() as MainActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
        binding.imgLoginEmailBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnLoginEmail.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_emailLoginFragment_to_rootFragment)
        }
        binding.textLoginEmailFind.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_emailLoginFragment_to_passwordFindFragment)
        }
    }
}