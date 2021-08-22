package com.untilled.roadcapture.features.root.capture

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptureFragment : Fragment() {
    private var _binding : FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCaptureBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabCaptureCapture.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_captureFragment_to_cameraFragment)
        }
        binding.imageviewCaptureBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}