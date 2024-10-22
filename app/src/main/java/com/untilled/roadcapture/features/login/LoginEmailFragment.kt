package com.untilled.roadcapture.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mobsandgeeks.saripaar.ValidationError
import com.mobsandgeeks.saripaar.Validator
import com.mobsandgeeks.saripaar.annotation.*
import com.untilled.roadcapture.data.datasource.api.dto.user.LoginRequest
import com.untilled.roadcapture.databinding.FragmentLoginEmailBinding
import com.untilled.roadcapture.features.base.BaseFragment
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToPasswordFind
import com.untilled.roadcapture.utils.navigateToRoot
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginEmailFragment : BaseFragment(), Validator.ValidationListener {

    private var _binding: FragmentLoginEmailBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var validator: Validator

    @Email
    @NotEmpty
    private lateinit var editEmail: EditText

    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC)
    @Length(max = 64)
    @NotEmpty
    private lateinit var editPassword: EditText

    private val viewModel: LoginEmailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginEmailBinding.inflate(layoutInflater, container, false)


        mainActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        validator.setValidationListener(this)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setOnClickListeners()
        observeData()
    }

    override fun onValidationSucceeded() {
        viewModel.login(
            LoginRequest(
                email = binding.edtLoginEmailInputemail.text.toString(),
                password = binding.edtLoginEmailPwdinput.text.toString(),
            )
        )
    }

    override fun onValidationFailed(errors: MutableList<ValidationError>?) {
        errors?.forEach { error ->
            val view = error.view
            val message = error.getCollatedErrorMessage(requireContext())

            if(view is EditText) {
                view.error = message
            } else {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        editEmail = binding.edtLoginEmailInputemail
        editPassword = binding.edtLoginEmailPwdinput
    }

    private fun observeData() {
        viewModel.viewEvent.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                when(it.first) {
                    LoginEmailViewModel.EVENT_NAVIGATE_TO_ROOT -> {
                        navigateToRoot()
                    }
                }
            }
        }
        viewModel.loadingEvent.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { event ->
                if(event) {
                    mainActivity().showLoading("${this::class.java.simpleName}LoadingDialog")
                } else {
                    mainActivity().dismissLoading()
                }
            }
        }
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private fun setOnClickListeners() {
        binding.imgLoginEmailBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
        binding.btnLoginEmailConfirm.setOnClickListener {
            validator.validate()
        }
        binding.textLoginEmailFind.setOnClickListener {
            navigateToPasswordFind()
        }
    }
}