package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.repository.token.dto.NaverOAuthTokenArgs
import com.untilled.roadcapture.databinding.FragmentLoginBinding
import com.untilled.roadcapture.utils.navigationHeight
import com.untilled.roadcapture.utils.setStatusBarOrigin
import com.untilled.roadcapture.utils.setStatusBarTransparent
import com.untilled.roadcapture.utils.statusBarHeight
import com.untilled.roadcapture.utils.instance.OAuthLoginInstances
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val buttonContainerOnClickListener: (View?) -> Unit = {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_loginFragment_to_emailLoginFragment)
    }

    private val signupOnClickListener: (View?) -> Unit = {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private val viewModel: LoginViewModel by viewModels()

    private val naverOAuthLoginHandler: OAuthLoginHandler = NonLeakNaverOAuthLoginHandler()

    private val isLoadingObserver = { isLoading: Boolean ->
        if (isLoading) {
            Logger.d("loading...")
        }
    }

    private val isLoginCompleteObserver = { isLoginComplete: Boolean ->
        if (isLoginComplete) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
        }
    }

    private val errorObserver = { error: String ->
        if(error.isNotBlank()) Toast.makeText(requireContext(), "error: $error", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        initData()

        return binding.root
    }

    private fun initData() {
        OAuthLoginInstances.naverOAuthLoginInstance.init(
            requireActivity(),
            BuildConfig.SOCIAL_NAVER_CLIENT_ID,
            BuildConfig.SOCIAL_NAVER_CLIENT_SECRET,
            BuildConfig.SOCIAL_NAVER_CLIENT_NAME
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().setStatusBarOrigin()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setStatusBarTransparent()
        binding.constraintLoginInnerContainer.setPadding(
            0,
            requireContext().statusBarHeight(),
            0,
            requireContext().navigationHeight()
        )

        setOAuthLoginHandlers()
        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        viewModel.isLoginComplete.observe(viewLifecycleOwner, isLoginCompleteObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private fun setOAuthLoginHandlers() {
        binding.btnLoginNaver.setOAuthLoginHandler(naverOAuthLoginHandler)
    }

    private fun setOnClickListeners() {
        binding.constraintLoginBtnContainer.setOnClickListener(buttonContainerOnClickListener)
        binding.textLoginSignup.setOnClickListener(signupOnClickListener)
        binding.btnLoginNaver.setOnClickListener {
            OAuthLoginInstances.naverOAuthLoginInstance.startOauthLoginActivity(requireActivity(), naverOAuthLoginHandler)
        }
    }

    private inner class NonLeakNaverOAuthLoginHandler : OAuthLoginHandler() {
        override fun run(success: Boolean): Unit = requireContext().run {
            if (success) {
                viewModel.saveNaverOAuthToken(NaverOAuthTokenArgs(
                    accessToken = OAuthLoginInstances.naverOAuthLoginInstance.getAccessToken(this),
                    expiresIn = OAuthLoginInstances.naverOAuthLoginInstance.getExpiresAt(this).toInt(),
                    refreshToken = OAuthLoginInstances.naverOAuthLoginInstance.getRefreshToken(this),
                    tokenType = OAuthLoginInstances.naverOAuthLoginInstance.getTokenType(this),
                ))

                viewModel.socialLogin(SocialType.NAVER)
            } else {
                Toast.makeText(
                    this,
                    "errorCode: ${
                        OAuthLoginInstances.naverOAuthLoginInstance.getLastErrorCode(
                            this
                        )
                    }, errorDesc: ${
                        OAuthLoginInstances.naverOAuthLoginInstance.getLastErrorDesc(this)
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}