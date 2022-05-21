package com.untilled.roadcapture.features.root.capture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumCreateRequest
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentAlbumRegistrationBinding
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToRoot
import com.untilled.roadcapture.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumRegistrationFragment : Fragment() {
    private var _binding: FragmentAlbumRegistrationBinding? = null
    val binding get() = _binding!!
    private var picture: Picture? = null
    private val viewModel: AlbumRegistrationViewModel by viewModels()

    private val checkOnClickListener: (View?) -> Unit = {
        if(binding.edtAlbumregTitle.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "앨범 제목을 작성해주세요", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getPictures(
                AlbumCreateRequest(
                    title = binding.edtAlbumregTitle.text.toString(),
                    description = binding.edtAlbumregDesc.text.toString()
                )
            )
            binding.progressAlbumregLoading.isVisible = true
        }
    }

    private val isCompleteObserver: (Boolean) -> Unit = { isComplete ->
        binding.progressAlbumregLoading.isVisible = false
        if(isComplete) {
            showSnackbar(requireView(),"앨범이 등록되었습니다.")
            navigateToRoot()
        } else {
            showSnackbar(requireView(),"앨범 등록에 실패했습니다.")
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

        getNavArgs()
        initViews()
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.isComplete.observe(viewLifecycleOwner, isCompleteObserver)
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