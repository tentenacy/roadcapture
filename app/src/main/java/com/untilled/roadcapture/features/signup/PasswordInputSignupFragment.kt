package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPasswordInputSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordInputSignupFragment : Fragment() {

    private var _binding: FragmentPasswordInputSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordInputSignupBinding.inflate(layoutInflater, container, false)

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

    private fun setOnClickListeners() {
        binding.imageviewPasswordInputSignupBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textviewPasswordInputSignupNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_passwordInputSignupFragment_to_usernameInputSignupFragment)
        }
    }
}