package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.untilled.roadcapture.BuildConfig
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.social.NaverOAuthToken
import com.untilled.roadcapture.databinding.FragmentLoginBinding
import com.untilled.roadcapture.utils.extension.navigationHeight
import com.untilled.roadcapture.utils.extension.setStatusBarOrigin
import com.untilled.roadcapture.utils.extension.setStatusBarTransparent
import com.untilled.roadcapture.utils.extension.statusBarHeight
import com.untilled.roadcapture.utils.instances.OAuthLoginInstances
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

    private val naverOAuthLoginHandler: OAuthLoginHandler = NonLeakOAuthLoginHandler()

    private inner class NonLeakOAuthLoginHandler : OAuthLoginHandler() {
        override fun run(success: Boolean): Unit = requireContext().run {
            if (success) {
                //리소스 서버로부터 토큰을 받아 SharedPreferences에 저장하고,
                NaverOAuthToken.accessToken = OAuthLoginInstances.naverOAuthLoginInstance.getAccessToken(this)
                NaverOAuthToken.refreshToken = OAuthLoginInstances.naverOAuthLoginInstance.getRefreshToken(this)
                NaverOAuthToken.expiresIn = OAuthLoginInstances.naverOAuthLoginInstance.getExpiresAt(this).toInt()
                NaverOAuthToken.tokenType = OAuthLoginInstances.naverOAuthLoginInstance.getTokenType(this)

                //액세스토큰으로 우리 서버에 회원가입 요청


                //이어서 로그인 요청


                //우리 서버로부터 액세스토큰을 받아 Room에 저장


                //로그인 성공하면 홈으로 이동


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
            requireContext(),
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
    }

    private fun setOAuthLoginHandlers() {
        binding.btnLoginNaver.setOAuthLoginHandler(naverOAuthLoginHandler)
    }

    private fun setOnClickListeners() {
        binding.constraintLoginBtnContainer.setOnClickListener(buttonContainerOnClickListener)
        binding.textLoginSignup.setOnClickListener(signupOnClickListener)
    }
}