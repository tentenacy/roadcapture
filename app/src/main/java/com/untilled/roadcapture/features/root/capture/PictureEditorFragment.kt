package com.untilled.roadcapture.features.root.capture

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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
import retrofit2.http.POST
import java.util.*

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!
    private var picture: Picture? = null
    private var mode = POST

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

        getNavArgs()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imgPictureEditorBack.setOnClickListener {
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

        binding.imgPictureEditorCheck.setOnClickListener {
            if(mode == POST) {
                viewModel.insertPicture(makePicture())
            } else if (mode == EDIT) {
                viewModel.updatePicture(makePicture())
            }
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_pictureEditorFragment_to_captureFragment)
        }

        binding.imgPictureEditorDelete.setOnClickListener {
            showDeletePictureAskingDialog {
                // todo 사진 삭제 기능
            }
        }

        binding.checkboxPictureEditorThumbnail.setOnCheckedChangeListener { _, isChecked ->
            picture?.thumbnail = isChecked
        }
    }

    private fun makePicture(): Picture = picture!!.apply {
            description = binding.edtPictureEditorDesc.text.toString()
    }

    private fun getNavArgs() {
        val args: PictureEditorFragmentArgs by navArgs()
        if (args.picture != null) {
            mode = when(args.picture!!.id) {
                0L -> POST      // id가 0이면 새로 등록
                else -> EDIT    // 아니면 기존 picture 수정
            }
            picture = args.picture
            binding.picture = picture
        }
    }

    private fun showDeletePictureAskingDialog(logic: () -> Unit) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dlg_picture_delete, null)

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.text_dlgpicturedelete_confirm)?.setOnClickListener {
            logic()
            dialog.dismiss()
        }
        dialogView.findViewById<TextView>(R.id.text_dlgpicturedelete_cancel)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        private const val POST = 100
        private const val EDIT = 101
    }
}