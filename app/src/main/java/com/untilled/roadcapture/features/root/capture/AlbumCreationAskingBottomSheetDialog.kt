package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.BottomsheetAlbumcreationBinding
import com.untilled.roadcapture.features.root.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumCreationAskingBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding : BottomsheetAlbumcreationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetAlbumcreationBinding.inflate(inflater, container, false)

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
        binding.btnDlgalbumcreationConfirm.setOnClickListener {
            Navigation.findNavController((parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_captureFragment)
            dismiss()
        }
        binding.btnDlgalbumcreationCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}