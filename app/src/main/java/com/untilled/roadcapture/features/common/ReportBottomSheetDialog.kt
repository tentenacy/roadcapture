package com.untilled.roadcapture.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.databinding.BottomsheetReportBinding

class ReportBottomSheetDialog(private val listener: () -> Unit) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetReportBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetReportBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        expandFullHeight()
    }

    private fun setOnClickListeners(){
        binding.textBottomsheetReportConfirm.setOnClickListener { listener }
        binding.textBottomsheetReportCancel.setOnClickListener { dismiss() }
    }

    private fun expandFullHeight() {
        BottomSheetBehavior.from(dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!).state =
            BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}