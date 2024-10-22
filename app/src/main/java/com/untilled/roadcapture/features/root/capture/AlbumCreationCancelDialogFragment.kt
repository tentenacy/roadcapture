package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.untilled.roadcapture.databinding.DlgAlbumcreationcancelBinding
import com.untilled.roadcapture.databinding.DlgPictureDeleteBinding
import com.untilled.roadcapture.features.base.BaseDialogFragment

class AlbumCreationCancelDialogFragment(private val listener: () -> Unit) :
    BaseDialogFragment() {

    private var _binding: DlgAlbumcreationcancelBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgAlbumcreationcancelBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlgalbumcreationcancelConfirm.setOnClickListener {
            listener()
            dismiss()
        }
        binding.textDlgalbumcreationcancelCancel.setOnClickListener(dismissOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}