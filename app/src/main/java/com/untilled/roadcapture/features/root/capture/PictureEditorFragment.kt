package com.untilled.roadcapture.features.root.capture

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!
    private var picture: Picture? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: PictureEditorFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture
            binding.picture = picture
            if(picture?.imageUri != null) {
                binding.imageviewPictureEditorRemove.isVisible = true
            }
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureEditorBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.imageviewPictureEditorRemove.setOnClickListener {
            picture?.imageUri = null
            removeImage()
        }

        binding.textviewPictureEditorPlaceUserInput.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    PictureEditorFragmentDirections.actionPictureEditorFragmentToSearchPlaceFragment(
                        picture = makePicture()
                    )
                )
        }

        binding.textviewPictureEditorDateUserInput.setOnClickListener {
            onCreateDatePicker()
        }

        binding.imageviewPictureEditorImage.setOnClickListener {
            // if (viewModel.isRemoved.value == true) {
            //     //Todo 카메라 찍기 or 갤러리 선택
            // }
        }

        binding.imageviewPictureEditorCheck.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    PictureEditorFragmentDirections.actionPictureEditorFragmentToCaptureFragment(
                        picture = makePicture()
                    )
                )
        }
    }

    private fun removeImage() {
        binding.imageviewPictureEditorImage.setImageResource(R.drawable.plus_dotted_square)
        binding.imageviewPictureEditorRemove.isVisible = false
    }

    private fun onCreateDatePicker() {
        val date: LocalDate = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                val date = makeDateString(year, month + 1, dayOfMonth)
                picture?.date = date
                binding.textviewPictureEditorDateUserInput.text = date
            },
            date.year, date.monthValue - 1, date.dayOfMonth
        )
        datePickerDialog.apply {
            val cal = Calendar.getInstance()
            datePicker.maxDate = cal.timeInMillis
        }.show()
    }

    private fun makeDateString(year: Int, month: Int, dayOfMonth: Int): String =
        "${year}년 ${String.format("%02d", month)}월 ${String.format("%02d", dayOfMonth)}일"

    private fun makePicture(): Picture =
        Picture(
            imageUri = picture?.imageUri,
            date = picture?.date,
            searchResult = picture?.searchResult,
            name = binding.edittextPictureEditorNameUserInput.text.toString(),
            description = binding.editPictureEditorDescriptionUserInput.text.toString()
        )

}