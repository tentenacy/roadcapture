package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.data.entity.SearchResult
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.signup.SignupViewModel

class PictureEditorFragment : Fragment() {
    private var _binding : FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!
    private val viewModel : PictureEditorViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        binding.apply{
            lifecycleOwner = lifecycleOwner
            vm = viewModel
        }

        val args: PictureEditorFragmentArgs by navArgs()
        if(args.imageUri != null){
            viewModel.imageUri.value = args.imageUri
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.initProperty()
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

        binding.textviewPictureEditorPlaceUserInput.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_pictureEditorFragment_to_searchPlaceFragment)
        }
    }
}