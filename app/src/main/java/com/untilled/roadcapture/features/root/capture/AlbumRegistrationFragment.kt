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
import com.untilled.roadcapture.databinding.FragmentAlbumRegistrationBinding
import com.untilled.roadcapture.databinding.FragmentPictureEditorBinding
import com.untilled.roadcapture.utils.dateToString
import com.untilled.roadcapture.utils.getCalendar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlbumRegistrationFragment : Fragment() {
    private var _binding: FragmentAlbumRegistrationBinding? = null
    private val binding get() = _binding!!
    private var picture: Picture? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumRegistrationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: AlbumRegistrationFragmentArgs by navArgs()
//        if (args.picture != null) {
//            picture = args.picture
//
//            if(picture?.date.isNullOrBlank()){
//                picture?.date = dateToString(Calendar.getInstance())
//            }
//
//            binding.picture = picture
//        }

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewAlbumRegistrationBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}