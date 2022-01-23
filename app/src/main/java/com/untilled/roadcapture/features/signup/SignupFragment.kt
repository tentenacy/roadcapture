package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentSignupBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.features.login.LoginViewModel
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this).get(SignupViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        binding.apply{
            lifecycleOwner = lifecycleOwner
        }

        mainActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}