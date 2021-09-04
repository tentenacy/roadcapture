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
import com.untilled.roadcapture.databinding.FragmentUsernameInputSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsernameInputSignupFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentUsernameInputSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsernameInputSignupBinding.inflate(layoutInflater, container, false)
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
        binding.buttonUsernameInputSignupSubmit.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment as SignupFragment).binding.root)
                .navigate(R.id.action_signupFragment_to_rootFragment)
        }
        (parentFragment?.parentFragment as SignupFragment).binding.imageviewSignupBack.setOnClickListener {
            viewModel.username.value = ""
            requireActivity().onBackPressed()
        }
    }

    private fun observeValidation() {
        viewModel.username.observe(viewLifecycleOwner, Observer {
            if (it.length >= 2)
                binding.usernameInputSignupContainer.transitionToEnd()
            else
                binding.usernameInputSignupContainer.transitionToStart()
        })

    }
}