package com.untilled.roadcapture.features.root.capture

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import com.untilled.roadcapture.databinding.ModalBottomSheetAlbumCreationAskingBinding
import com.untilled.roadcapture.databinding.ModalBottomSheetAlbumRegistrationAskingBinding
import com.untilled.roadcapture.features.root.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumRegistrationAskingBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding : ModalBottomSheetAlbumRegistrationAskingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetAlbumRegistrationAskingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
        setOnClickListeners()
    }

    private fun expandFullHeight(){
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClickListeners(){
        binding.buttonAlbumRegistrationAskingCreate.setOnClickListener {
            //Todo 앨범 등록화면으로 이동
            dismiss()
        }
        binding.buttonAlbumRegistrationAskingCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}