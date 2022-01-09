package com.untilled.roadcapture.features.root.capture

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private val viewModel: PictureEditorViewModel by viewModels()

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

        // todo: 어떤 Fragment에서 왔는지 알아야 함 (수정 인지, 새로 만드는 것인지 판단 위해)
        val args: PictureEditorFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture

            if(picture?.createdAt.isNullOrBlank()){
                picture?.createdAt = dateToString(Calendar.getInstance())
            }

            binding.picture = picture
        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imagePictureEditorBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.textPictureEditorPlace.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(
                    PictureEditorFragmentDirections.actionPictureEditorFragmentToSearchPlaceFragment(
                        picture = makePicture()
                    )
                )
        }

        binding.textPictureEditorDate.setOnClickListener {
            onCreateDatePicker()
        }

        binding.imagePictureEditorCheck.setOnClickListener {
            // todo Room에 picture insert
            viewModel.insertPicture(picture!!)

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_pictureEditorFragment_to_captureFragment)
        }

        binding.imagePictureEditorDelete.setOnClickListener {
            showDeletePictureAskingDialog {
                // todo 사진 삭제 기능
            }
        }
    }

    private fun onCreateDatePicker() {
        val cal = getCalendar(binding.textPictureEditorDate.text.toString())

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                val date = dateToString(year, month + 1, dayOfMonth)
                picture?.createdAt = date
                binding.textPictureEditorDate.text = date
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
            imageUrl = picture?.imageUrl,
            createdAt = picture?.createdAt,
            lastModifiedAt = picture?.lastModifiedAt,
            description = binding.edtPictureEditorDescription.text.toString(),
            place = picture?.place
        )

    private fun showDeletePictureAskingDialog(logic: () -> Unit) {
        val layoutInflater = LayoutInflater.from(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dlg_picture_delete, null)

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