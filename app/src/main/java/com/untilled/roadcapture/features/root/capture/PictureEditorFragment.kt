package com.untilled.roadcapture.features.root.capture

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.untilled.roadcapture.R
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.POST

@AndroidEntryPoint
class PictureEditorFragment : Fragment() {
    private var _binding: FragmentPictureEditorBinding? = null
    private val binding get() = _binding!!
    private var picture: Picture? = null
    private var mode = POST

    private val viewModel: PictureEditorViewModel by viewModels()

    private val orderObserver: (Long) -> Unit = { order ->
        picture?.order = order
    }

    private val placeOnClickListener : (View?) -> Unit = {
        Navigation.findNavController(binding.root).navigate(
                PictureEditorFragmentDirections.actionPictureEditorFragmentToSearchPlaceFragment(
                    picture = makePicture()
                )
            )
    }

    private val checkOnClickListener : (View?) -> Unit = {
        if(picture?.place == null) {
            Toast.makeText(requireContext(), "장소를 등록해주세요", Toast.LENGTH_SHORT).show()
        } else {
            if (mode == POST) {
                viewModel.insertPicture(makePicture())
            } else if (mode == EDIT) {
                viewModel.updatePicture(makePicture())
            }
            Navigation.findNavController(binding.root)
                .navigate(PictureEditorFragmentDirections.actionPictureEditorFragmentToCaptureFragment())
        }
    }

    private val deleteOnClickListener : (View?) -> Unit = {
        showDeletePictureAskingDialog {
            if (mode == EDIT) {
                viewModel.deletePicture(makePicture())
            }
            Navigation.findNavController(binding.root).navigate(PictureEditorFragmentDirections.actionPictureEditorFragmentToCaptureFragment())
        }
    }

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
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.order.observe(viewLifecycleOwner, orderObserver)
    }

    private fun setOnClickListeners() {
        binding.imgPictureEditorBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.textPictureEditorPlace.setOnClickListener(placeOnClickListener)
        binding.imgPictureEditorCheck.setOnClickListener(checkOnClickListener)
        binding.imgPictureEditorDelete.setOnClickListener(deleteOnClickListener)
        binding.checkboxPictureEditorThumbnail.setOnCheckedChangeListener { _, isChecked -> picture?.thumbnail = isChecked }
    }

    private fun makePicture(): Picture = picture!!.apply {
        description = binding.edtPictureEditorDesc.text.toString()
    }

    private fun getNavArgs() {
        val args: PictureEditorFragmentArgs by navArgs()
        if (args.picture != null) {
            mode = if (args.picture!!.id == 0L) POST else EDIT
            if(args.picture!!.order == 0L) {
                viewModel.getNextOrder()
            }

            picture = args.picture
            binding.picture = picture
        }
    }

    private fun showDeletePictureAskingDialog(logic: () -> Unit) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dlg_picture_delete, null)

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