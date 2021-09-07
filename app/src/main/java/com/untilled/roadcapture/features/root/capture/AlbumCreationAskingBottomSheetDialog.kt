package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ModalBottomSheetAlbumCreationAskingBinding
import com.untilled.roadcapture.features.root.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumCreationAskingBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding : ModalBottomSheetAlbumCreationAskingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetAlbumCreationAskingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.buttonAlbumCreationAskingCreate.setOnClickListener {
            Navigation.findNavController((parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_captureFragment)
            dismiss()
        }
        binding.buttonAlbumCreationAskingCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}