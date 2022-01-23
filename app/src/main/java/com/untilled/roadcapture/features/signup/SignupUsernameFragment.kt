package com.untilled.roadcapture.features.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSignupUsernameBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToRoot
import com.untilled.roadcapture.utils.navigateToTermsOfService
import com.untilled.roadcapture.utils.signupFrom2Depth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupUsernameFragment : BaseFragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentSignupUsernameBinding? = null
    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private val usernameObserver: (String) -> Unit = {
        if (it.length in 2..12)
            binding.motionSignupUsernameContainer.transitionToEnd()
        else
            binding.motionSignupUsernameContainer.transitionToStart()
    }

    private val isLoggedInObserver: (Boolean) -> Unit = { isLoggedIn ->
        if (isLoggedIn) {
            signupFrom2Depth().navigateToRoot()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.edtSignupUsernameInput.setText("")
                findNavController().navigateUp()
            }
        }
        mainActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupUsernameBinding.inflate(layoutInflater, container, false)
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

        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSignupUsernameConfirm.setOnClickListener {
            viewModel.signup()
        }
        binding.textSignupUsernameTermsofservice.setOnClickListener {
            signupFrom2Depth().navigateToTermsOfService()
        }

        (parentFragment?.parentFragment as SignupFragment).binding.imgSignupBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }

    private fun observeData() {
        viewModel.username.observe(viewLifecycleOwner, usernameObserver)
        viewModel.isLoggedIn.observe(viewLifecycleOwner, isLoggedInObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
    }
}