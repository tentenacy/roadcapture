package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.untilled.roadcapture.databinding.DlgAlbumcreationcancelBinding
import com.untilled.roadcapture.databinding.DlgThumbnailSettingBinding
import com.untilled.roadcapture.features.base.BaseDialogFragment

class ThumbnailSettingDialogFragment : BaseDialogFragment() {

    private var _binding: DlgThumbnailSettingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DlgThumbnailSettingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textDlgthumbnailsettingConfirm.setOnClickListener(dismissOnClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}