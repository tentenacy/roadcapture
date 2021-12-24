package com.untilled.roadcapture.features.root.capture

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.utils.dateToString
import com.untilled.roadcapture.utils.getCalendar
import dagger.hilt.android.AndroidEntryPoint
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

            if(picture?.date.isNullOrBlank()){
                picture?.date = dateToString(Calendar.getInstance())
            }

            binding.picture = picture
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewPictureEditorBack.setOnClickListener {
            requireActivity().onBackPressed()
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

        binding.imageviewPictureEditorCheck.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    PictureEditorFragmentDirections.actionPictureEditorFragmentToCaptureFragment(
                        picture = makePicture()
                    )
                )
        }

        binding.imageviewPictureEditorDelete.setOnClickListener {
            showDeletePictureAskingDialog {
                // todo 사진 삭제 기능
            }
        }
    }

    private fun onCreateDatePicker() {
        val cal = getCalendar(binding.textviewPictureEditorDateUserInput.text.toString())

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                val date = dateToString(year, month + 1, dayOfMonth)
                picture?.date = date
                binding.textviewPictureEditorDateUserInput.text = date
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.apply {
            val cal = Calendar.getInstance()
            datePicker.maxDate = cal.timeInMillis
        }.show()
    }

    private fun makePicture(): Picture =
        Picture(
            imageUri = picture?.imageUri,
            date = picture?.date,
            searchResult = picture?.searchResult,
            name = binding.edittextPictureEditorNameUserInput.text.toString(),
            description = binding.editPictureEditorDescriptionUserInput.text.toString()
        )

    private fun showDeletePictureAskingDialog(logic: () -> Unit) {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog_delete_picture_asking, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        val textViewDelete = dialogView.findViewById<TextView>(R.id.textview_delete_picture_asking_delete)
        val textViewCancel = dialogView.findViewById<TextView>(R.id.textview_delete_picture_asking_cancel)

        textViewDelete?.setOnClickListener {
            logic()
            dialog.dismiss()
        }
        textViewCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}