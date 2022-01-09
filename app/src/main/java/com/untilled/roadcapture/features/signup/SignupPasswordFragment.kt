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
import com.untilled.roadcapture.databinding.FragmentSignupPwdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupPasswordFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentSignupPwdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupPwdBinding.inflate(layoutInflater, container, false)
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
        binding.btnSignupPwd.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_signupPasswordFragment_to_signupUsernameFragment)
        }
        (parentFragment?.parentFragment as SignupFragment).binding.imgSignupBack.setOnClickListener {
            viewModel.password.value = ""
            viewModel.passwordVerification.value = ""
            requireActivity().onBackPressed()
        }
    }

    private fun observeValidation() {

        viewModel.password.observe(viewLifecycleOwner, Observer {
            if (it.length >= 6)
                binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_end)
            else
                binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_start)
        })

        viewModel.passwordVerification.observe(viewLifecycleOwner, Observer {
            if (viewModel.password.value!!.length >= 6) {
                if (viewModel.password.value == it)
                    binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_end)
                else
                    binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_start)
            }
        })
    }
}
