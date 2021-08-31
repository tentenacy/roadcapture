package com.untilled.roadcapture.features.root.capture

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentCaptureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CaptureFragment : Fragment() {
    private var _binding: FragmentCaptureBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    // 갤러리 사진 가져오는 intent 콜백 등록
    private val getContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK) {
            Log.d("activityResult", it.toString())
            imageUri = it.data?.data
            Navigation.findNavController(binding.root)
                .navigate(CaptureFragmentDirections.actionCaptureFragmentToCropFragment(imageUri.toString()))
        }
    }

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
        binding.imageviewCaptureBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.fabCapturePickFromGallery.setOnClickListener {
            pickFromGallery()
        }
    }

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
            .setType(MediaStore.Images.Media.CONTENT_TYPE)
            .setType("image/*")

        getContent.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}