package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.databinding.FragmentAlbumRegistrationBinding
import com.untilled.roadcapture.features.common.NavHostViewModel
import com.untilled.roadcapture.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumRegistrationFragment : Fragment() {
    private var _binding: FragmentAlbumRegistrationBinding? = null
    val binding get() = _binding!!

    private val navHostViewModel: NavHostViewModel by viewModels({ requireParentFragment() })

    private val checkOnClickListener: (View?) -> Unit = checkOnClickListener@{
        if (binding.edtAlbumregTitle.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "앨범 제목을 작성해주세요", Toast.LENGTH_SHORT).show()
            return@checkOnClickListener
        }

        navHostViewModel.postAlbum(
            AlbumCreateRequest(
                title = binding.edtAlbumregTitle.text.toString(),
                description = binding.edtAlbumregDesc.text.toString()
            )
        )

        binding.progressAlbumregLoading.isVisible = true
    }

    private val postAlbumStateObserver: (Int) -> Unit = { state ->
        binding.progressAlbumregLoading.isVisible = false
        when (state) {
            SUCCESS -> {
                showSnackbar(requireView(), "앨범이 등록되었습니다.")
                navHostViewModel.postAlbumState.value = DEFAULT
                navigateToRoot()
            }
            FAILURE -> {
                showSnackbar(requireView(), "앨범 등록에 실패했습니다.")
                navHostViewModel.postAlbumState.value = DEFAULT
            }
        }
    }

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

        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        navHostViewModel.postAlbumState.observe(viewLifecycleOwner, postAlbumStateObserver)
    }

    private fun initViews() {
        navHostViewModel.pictureList.forEach {
            if (it.thumbnail) {
                binding.picture = it
                return@forEach
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageAlbumregBack.setOnClickListener { mainActivity().onBackPressed() }
        binding.imageAlbumregCheck.setOnClickListener(checkOnClickListener)
    }
}