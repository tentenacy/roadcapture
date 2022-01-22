package com.untilled.roadcapture.features.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mobsandgeeks.saripaar.annotation.Password
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentSignupPwdBinding
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToSignupUsername
import com.untilled.roadcapture.utils.signupFromChild
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignupPasswordFragment : Fragment() {

    private val viewModel: SignupViewModel by viewModels({ requireParentFragment() })

    private var _binding: FragmentSignupPwdBinding? = null
    val binding get() = _binding!!

    private val passwordVerificationObserver: (String) -> Unit = { passwordVerification ->
        if (passwordVerification.length in 8..64 && Pattern.matches("(?=.*[a-zA-Z])(?=.*[\\d]).+", passwordVerification)) {
            if (viewModel.password.value == passwordVerification)
                binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_end)
            else
                binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_start)
        } else if(passwordVerification.isNotBlank())
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_button_start)
    }

    private val passwordObserver: (String) -> Unit = { password ->
        if (password.length in 8..64 && Pattern.matches("(?=.*[a-zA-Z])(?=.*[\\d]).+", password)) {
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_end)
        } else  {
            binding.motionSignupPwdContainer.transitionToState(R.id.password_input_signup_verification_start)
        }
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.edtSignupPwdInput.setText("")
                binding.edtSignupPwdConfirmInput.setText("")
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

        observeData()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSignupPwdConfirm.setOnClickListener {
            navigateToSignupUsername()
        }
        signupFromChild().binding.imgSignupBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }

    private fun observeData() {
        viewModel.password.observe(viewLifecycleOwner, passwordObserver)
        viewModel.passwordVerification.observe(viewLifecycleOwner, passwordVerificationObserver)
    }
}
