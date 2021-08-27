package com.untilled.roadcapture.features.signup

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
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

    private val viewModel: SignupViewModel by viewModels ({requireParentFragment()})

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
            requireActivity().onBackPressed()
        }
    }

    private fun observeValidation(){
        viewModel.password.observe(viewLifecycleOwner, Observer{
            binding.edittextPasswordInputSignupVerification.isEnabled = it.length >= 6
        })
        viewModel.passwordVerification.observe(viewLifecycleOwner, Observer{
            if(binding.edittextPasswordInputSignup.text.toString() == it && binding.edittextPasswordInputSignup.text.toString().length >= 6){
                binding.buttonPasswordInputSignupNext.isEnabled = true
                binding.buttonPasswordInputSignupNext.setBackgroundColor(Color.parseColor("#3d86c7"))
                binding.buttonPasswordInputSignupNext.setTextColor(Color.WHITE)
            }
            else{
                binding.buttonPasswordInputSignupNext.isEnabled = false
                binding.buttonPasswordInputSignupNext.setBackgroundColor(Color.parseColor("#EFEFEF"))
                binding.buttonPasswordInputSignupNext.setTextColor(Color.BLACK)
            }
        })
    }
}