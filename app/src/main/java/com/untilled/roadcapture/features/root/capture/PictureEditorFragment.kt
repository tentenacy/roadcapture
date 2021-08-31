package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding

class PictureEditorFragment : Fragment() {
    private var _binding : FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        val args: PictureEditorFragmentArgs by navArgs()
        binding.imageUri = args.imageUrl

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureEditorBack.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.imageviewPictureEditorRemove.setOnClickListener {
            binding.imageviewPictureEditorImage.setImageResource(R.drawable.plus_dotted_square)
            it.visibility = View.GONE
        }
    }
}