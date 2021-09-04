package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPasswordInputSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordInputSignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentPasswordInputSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordInputSignupBinding.inflate(layoutInflater, container, false)
        binding.apply {
            lifecycleOwner = lifecycleOwner
            vm = viewModel
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValidation()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonPasswordInputSignupNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_passwordInputSignupFragment_to_usernameInputSignupFragment)
        }
        (parentFragment?.parentFragment as SignupFragment).binding.imageviewSignupBack.setOnClickListener {
            viewModel.password.value = ""
            viewModel.passwordVerification.value = ""
            requireActivity().onBackPressed()
        }
    }

    private fun observeValidation() {

        viewModel.password.observe(viewLifecycleOwner, Observer {
            if (it.length >= 6)
                binding.passwordInputSignupContainer.transitionToState(R.id.password_input_signup_verification_end)
            else
                binding.passwordInputSignupContainer.transitionToState(R.id.password_input_signup_verification_start)
        })

        viewModel.passwordVerification.observe(viewLifecycleOwner, Observer {
            if (viewModel.password.value!!.length >= 6) {
                if (viewModel.password.value == it)
                    binding.passwordInputSignupContainer.transitionToState(R.id.password_input_signup_button_end)
                else
                    binding.passwordInputSignupContainer.transitionToState(R.id.password_input_signup_button_start)
            }
        })
    }
}
