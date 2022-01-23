package com.untilled.roadcapture.features.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.untilled.roadcapture.databinding.FragmentLoginBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.features.login.handler.FacebookOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.GoogleOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.KakaoOAuthLoginHandler
import com.untilled.roadcapture.features.login.handler.NaverOAuthLoginHandler
import com.untilled.roadcapture.utils.*
import com.untilled.roadcapture.utils.constant.scope.SocialScopeConstant
import com.untilled.roadcapture.utils.type.SocialType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

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

    @Inject
    lateinit var naverLoginManager: OAuthLogin

    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private val btnContainerOnClickListener: (View?) -> Unit = {
        navigateToEmailLogin()
    }

    private val signupOnClickListener: (View?) -> Unit = {
        navigateToSignup()
    }

    private val naverLoginOnClickListener: (View?) -> Unit = {
        naverLoginManager.startOauthLoginActivity(
            mainActivity(),
            naverOAuthLoginHandler
        )
    }

    private val kakaoLoginOnClickListener: (View?) -> Unit = {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(
                requireContext(),
                callback = kakaoOAuthLoginHandler
            )
        } else {
            UserApiClient.instance.loginWithKakaoAccount(
                requireContext(),
                callback = kakaoOAuthLoginHandler
            )
        }
    }

    private val googleLoginOnClickListener: (View?) -> Unit = {
        mainActivity().activityResultFactory.launch(
            googleSignInClient.signInIntent,
            googleOAuthLoginHandler
        )
    }

    private val loginObserver: (SocialType?) -> Unit = { socialType ->
        if(socialType != null) {
            mainActivity().viewModel.registerToOAuthLoginManagerSubject(socialType)
        }
        navigateToRoot()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.autoLogin()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)
        observeData()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintLoginInnerContainer.setStatusBarTransparent(mainActivity())

        setOAuthLoginHandlers()
        setOnClickListeners()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mainActivity().setStatusBarOrigin()

        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initViews() {
        binding.loginbtnLoginFacebook.run {
            setPermissions(listOf(SocialScopeConstant.FACEBOOK_SCOPE_EMAIL, SocialScopeConstant.FACEBOOK_SCOPE_PUBLIC_PROFILE))
            fragment = this@LoginFragment
            registerCallback(callbackManager, facebookOAuthLoginHandler)
        }
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
        viewModel.isLoggedIn.observe(viewLifecycleOwner, loginObserver)
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