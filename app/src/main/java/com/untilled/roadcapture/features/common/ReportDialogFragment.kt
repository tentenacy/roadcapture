package com.untilled.roadcapture.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.untilled.roadcapture.databinding.DlgReportBinding
import com.untilled.roadcapture.features.base.BaseDialogFragment

class ReportDialogFragment(private val listener: () -> Unit) :
    BaseDialogFragment() {

    private var _binding: DlgReportBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgReportBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlgreportConfirm.setOnClickListener {
            listener()
        }
        binding.dlgreportCancel.setOnClickListener(dismissOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}