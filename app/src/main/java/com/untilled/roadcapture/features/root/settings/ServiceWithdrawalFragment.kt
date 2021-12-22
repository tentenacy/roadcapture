package com.untilled.roadcapture.features.root.settings

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentServiceWithdrawalBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceWithdrawalFragment : Fragment(){
    private var _binding: FragmentServiceWithdrawalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentServiceWithdrawalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        checkEmail()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners(){
        binding.imageviewServiceWithdrawalBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun checkEmail(){
        binding.edittextServiceWithdrawalConfirmEmail.addTextChangedListener (object:TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Tag",text.toString())
                if(text.toString() == "kwangddang11@naver.com"){
                    binding.buttonServiceWithdrawal.isClickable = true
                    binding.buttonServiceWithdrawal.setBackgroundColor(Color.parseColor("#3d86c7"))
                    binding.buttonServiceWithdrawal.setTextColor(Color.WHITE)
                }
            }

            override fun afterTextChanged(text: Editable?) {
            }
        }
        )
    }
}