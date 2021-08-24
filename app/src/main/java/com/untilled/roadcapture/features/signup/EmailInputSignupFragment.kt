package com.untilled.roadcapture.features.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentEmailInputSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailInputSignupFragment : Fragment() {

    private var _binding: FragmentEmailInputSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailInputSignupBinding.inflate(layoutInflater, container, false)
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

        binding.textviewEmailInputSignupNext.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_emailInputSignupFragment_to_passwordInputSignupFragment)
        }
        (parentFragment?.parentFragment as SignupFragment).binding.imageviewSignupBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}