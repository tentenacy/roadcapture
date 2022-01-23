package com.untilled.roadcapture.features.settings

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentWithdrawalBinding
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalFragment : Fragment(){
    private var _binding: FragmentWithdrawalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWithdrawalBinding.inflate(inflater,container,false)
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
        binding.imgWithdrawalBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }

    private fun checkEmail(){
        binding.edtWithdrawalInput.addTextChangedListener (object:TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Tag",text.toString())
                if(text.toString() == "kwangddang11@naver.com"){
                    binding.btnWithdrawalConfirm.isClickable = true
                    binding.btnWithdrawalConfirm.setBackgroundColor(Color.parseColor("#3d86c7"))
                    binding.btnWithdrawalConfirm.setTextColor(Color.WHITE)
                }
            }

            override fun afterTextChanged(text: Editable?) {
            }
        }
        )
    }
}