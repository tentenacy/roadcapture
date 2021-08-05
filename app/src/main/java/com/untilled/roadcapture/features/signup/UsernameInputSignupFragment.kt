package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentUsernameInputSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsernameInputSignupFragment : Fragment() {
    private var _binding: FragmentUsernameInputSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsernameInputSignupBinding.inflate(layoutInflater, container, false)

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
        binding.imageviewUsernameInputSignupBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textviewUsernameInputSignupSubmit.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_usernameInputSignupFragment_to_rootFragment)
        }
    }
}