package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.untilled.roadcapture.databinding.DlgPictureDeleteBinding
import com.untilled.roadcapture.features.base.BaseDialogFragment

class PictureDeleteDialogFragment(private val listener: () -> Unit) :
    BaseDialogFragment() {

    private var _binding: DlgPictureDeleteBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgPictureDeleteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlgpicturedeleteConfirm.setOnClickListener {
            listener()
        }
        binding.textDlgpicturedeleteCancel.setOnClickListener(dismissOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}