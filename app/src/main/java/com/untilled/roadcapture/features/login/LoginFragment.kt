package com.untilled.roadcapture.features.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentLoginBinding
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.instance.OAuthLoginInstances
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var naverOAuthLoginHandler: NaverOAuthLoginHandler

    @Inject
    lateinit var facebookOAuthLoginHandler: FacebookOAuthLoginHandler

    @Inject
    lateinit var kakaoOAuthLoginHandler: KakaoOAuthLoginHandler

    @Inject
    lateinit var googleOAuthLoginHandler: GoogleOAuthLoginHandler

    @Inject
    lateinit var callbackManager: CallbackManager

    private val loadingDialog : LoadingDialog by lazy{
        LoadingDialog(requireContext())
    }

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

    private val kakaoLoginOnClickListener: (View?) -> Unit = {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoOAuthLoginHandler)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoOAuthLoginHandler)
        }
    }

    private val googleLoginOnClickListener: (View?) -> Unit = {
        mainActivity().activityResultFactory.launch(googleSignInClient.signInIntent, googleOAuthLoginHandler)
    }

    private val isLoadingObserver = { isLoading: Boolean ->
        if (isLoading) {
            Logger.d("loading...")
            Log.d("Test","???")
            loadingDialog.show()
            viewModel.isLoggingIn.observe(viewLifecycleOwner, isLoggingInObserver)
        } else {
            viewModel.isLoggingIn.removeObservers(viewLifecycleOwner)
        }
    }

    private val isLoggingInObserver = { isLoggingIn: Boolean ->
        if (!isLoggingIn) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRootFragment())
            loadingDialog.dismiss()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintLoginInnerContainer.setStatusBarTransparent(requireActivity())

        setOAuthLoginHandlers()
        setOnClickListeners()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().setStatusBarOrigin()

        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initData() {
        binding.loginbtnLoginFacebook.run {
            setReadPermissions(listOf("email", "public_profile"))
            fragment = this@LoginFragment
            registerCallback(callbackManager, facebookOAuthLoginHandler)
        }
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
        binding.imgLoginKakao.setOnClickListener(kakaoLoginOnClickListener)
        binding.signinbtnLoginGoogle.setOnClickListener(googleLoginOnClickListener)
    }
}