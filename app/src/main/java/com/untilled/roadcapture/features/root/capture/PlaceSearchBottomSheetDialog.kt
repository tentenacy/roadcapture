package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.databinding.BottomsheetPlacesearchBinding

class PlaceSearchBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetPlacesearchBinding? = null
    private val binding get() = _binding!!

    lateinit var searchOnClickListener: (View?) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetPlacesearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageDlgplacesearchClose.setOnClickListener { dismiss() }
        binding.imageDlgplacesearchSearch.setOnClickListener(searchOnClickListener)
        binding.imageDlgplacesearchMap.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}