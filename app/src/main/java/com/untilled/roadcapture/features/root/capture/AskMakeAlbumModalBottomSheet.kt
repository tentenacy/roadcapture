package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ModalBottomSheetAskMakeAlbumBinding

class AskMakeAlbumModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding : ModalBottomSheetAskMakeAlbumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetAskMakeAlbumBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickedListeners()
    }

    private fun onClickedListeners(){
        binding.buttonAskMakeAlbumYes.setOnClickListener {
            dismiss()
        }
        binding.buttonAskMakeAlbumNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}