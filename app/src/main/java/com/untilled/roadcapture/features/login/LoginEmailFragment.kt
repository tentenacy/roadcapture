package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.datasource.api.dto.user.LoginRequest
import com.untilled.roadcapture.databinding.FragmentLoginEmailBinding
import com.untilled.roadcapture.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailFragment : BaseFragment() {
    private var _binding: FragmentLoginEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginEmailViewModel by viewModels()

    private val isLoggedInObserver: (Boolean) -> Unit = { isLoggedIn ->
        if (isLoggedIn) {
            findNavController().navigate(LoginEmailFragmentDirections.actionEmailLoginFragmentToRootFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginEmailBinding.inflate(layoutInflater, container, false)
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
        observeData()
    }

    private fun setOnClickListeners() {
        binding.imgLoginEmailBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnLoginEmailConfirm.setOnClickListener {
            viewModel.login(
                LoginRequest(
                    email = binding.edtLoginEmailInputemail.text.toString(),
                    password = binding.edtLoginEmailPwdinput.text.toString(),
                )
            )
        }
        binding.textLoginEmailFind.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_emailLoginFragment_to_passwordFindFragment)
        }
    }

    private fun observeData() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner, isLoggedInObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }
}