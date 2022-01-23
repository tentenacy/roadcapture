package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentAlbumRegistrationBinding
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumRegistrationFragment : Fragment() {
    private var _binding: FragmentAlbumRegistrationBinding? = null
    private val binding get() = _binding!!
    private var picture: Picture? = null

    private val checkOnClickListener: (View?) -> Unit = {
        if(binding.edtAlbumregTitle.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "앨범 제목을 작성해주세요", Toast.LENGTH_SHORT).show()
        } else {
            // todo: 앨범 등록 로직
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumRegistrationBinding.inflate(inflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        initViews()
        setOnClickListeners()
    }

    private fun initViews() {
        if(picture != null) {
            binding.picture = picture!!
        }
    }

    private fun getNavArgs() {
        val args: AlbumRegistrationFragmentArgs by navArgs()
        if (args.picture != null) {
            picture = args.picture
        }
    }

    private fun setOnClickListeners() {
        binding.imageAlbumregBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.imageAlbumregCheck.setOnClickListener(checkOnClickListener)
    }
}