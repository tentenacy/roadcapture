package com.untilled.roadcapture.features.root.capture

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import java.time.LocalDate

class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PictureEditorViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureEditorBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = lifecycleOwner
            vm = viewModel
        }

        val args: PictureEditorFragmentArgs by navArgs()
        if (args.imageUri != null && (viewModel.isRemoved.value == false)) {
            viewModel.imageUri.value = args.imageUri
        } else {
            removeImage()
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
        binding.imageviewPictureEditorBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.imageviewPictureEditorRemove.setOnClickListener {
            viewModel.isRemoved.value = true
            viewModel.imageUri.value = null
            removeImage()
        }

        binding.textviewPictureEditorPlaceUserInput.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_pictureEditorFragment_to_searchPlaceFragment)
        }

        binding.textviewPictureEditorDateUserInput.setOnClickListener {
            onCreateDatePicker()
        }

        binding.imageviewPictureEditorImage.setOnClickListener {
            if(viewModel.isRemoved.value == true){
                //Todo 카메라 찍기 or 갤러리 선택
            }
        }
    }

    private fun removeImage() {
        binding.imageviewPictureEditorImage.setImageResource(R.drawable.plus_dotted_square)
        binding.imageviewPictureEditorRemove.visibility = View.GONE
    }

    private fun onCreateDatePicker() {
        val date: LocalDate = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                val date = makeDate(year, month, dayOfMonth)
                viewModel.date.value = date
                binding.textviewPictureEditorDateUserInput.text = date
            },
            date.year, date.monthValue - 1, date.dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun makeDate(year: Int, month: Int, dayOfMonth: Int): String =
        "${year}년 ${month}월 ${dayOfMonth}일"
}