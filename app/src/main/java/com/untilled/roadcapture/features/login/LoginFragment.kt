package com.untilled.roadcapture.features.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import com.untilled.roadcapture.data.repository.token.dto.OAuthTokenArgs
import com.untilled.roadcapture.databinding.FragmentLoginBinding
import com.untilled.roadcapture.utils.instance.OAuthLoginInstances
import com.untilled.roadcapture.utils.navigationHeight
import com.untilled.roadcapture.utils.setStatusBarOrigin
import com.untilled.roadcapture.utils.setStatusBarTransparent
import com.untilled.roadcapture.utils.statusBarHeight
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var naverOAuthLoginHandler: OAuthLoginHandler

    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val btnContainerOnClickListener: (View?) -> Unit = {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_loginFragment_to_emailLoginFragment)
    }

    private val signupOnClickListener: (View?) -> Unit = {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private val naverLoginOnClickListener: (View?) -> Unit = {
        OAuthLoginInstances.naverOAuthLoginInstance.startOauthLoginActivity(
            requireActivity(),
            naverOAuthLoginHandler
        )
    }

    private val googleLoginOnClickListener: (View?) -> Unit = {
        activityResultFactory.launch(googleSignInClient.signInIntent) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleGoogleLoginResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))
            }
        }
    }

    private val isLoadingObserver = { isLoading: Boolean ->
        if (isLoading) {
            Logger.d("loading...")
            viewModel.isLoggingIn.observe(viewLifecycleOwner, isLoggingInObserver)
        } else {
            viewModel.isLoggingIn.removeObserver(isLoggingInObserver)
        }
    }

    private val isLoggingInObserver = { isLoggingIn: Boolean ->
        if (!isLoggingIn) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
        }
    }

    private val errorObserver = { error: String ->
        if (error.isNotBlank()) Toast.makeText(
            requireContext(),
            "error: $error",
            Toast.LENGTH_SHORT
        ).show()
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

    private fun initData() {
        OAuthLoginInstances.naverOAuthLoginInstance.init(
            requireActivity(),
            BuildConfig.SOCIAL_NAVER_CLIENT_ID,
            BuildConfig.SOCIAL_NAVER_CLIENT_SECRET,
            BuildConfig.SOCIAL_NAVER_CLIENT_NAME
        )
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private fun setOAuthLoginHandlers() {
        binding.btnLoginNaver.setOAuthLoginHandler(naverOAuthLoginHandler)
    }

    private fun setOnClickListeners() {
        binding.constraintLoginBtnContainer.setOnClickListener(btnContainerOnClickListener)
        binding.textLoginSignup.setOnClickListener(signupOnClickListener)
        binding.btnLoginNaver.setOnClickListener(naverLoginOnClickListener)
//        binding.frameLoginGoogle.setOnClickListener(imgGoogleLoginOnClickListener)
        binding.signinbtnLoginGoogle.setOnClickListener(googleLoginOnClickListener)
    }

    private fun handleGoogleLoginResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            viewModel.saveOAuthToken(
                OAuthTokenArgs(
                    accessToken = account.idToken!!,
                    refreshToken = null,
                )
            )

            viewModel.socialLogin(SocialType.GOOGLE)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Logger.w("signInResult:failed code=${e.statusCode}")
        }
    }
}