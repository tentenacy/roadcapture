package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSignupEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupEmailFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentSignupEmailBinding? = null
    private val binding get() = _binding!!

    private val emailObserver: (String) -> Unit = { email ->
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.motionSignupEmailContainer.transitionToEnd()
        } else {
            binding.motionSignupEmailContainer.transitionToStart()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupEmailBinding.inflate(layoutInflater, container, false)
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

        binding.btnSignupEmailConfirm.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_signupEmailFragment_to_signupPasswordFragment)
        }
        (parentFragment?.parentFragment as SignupFragment).binding.imgSignupBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun observeValidation() {
        viewModel.email.observe(viewLifecycleOwner, emailObserver)
    }

}