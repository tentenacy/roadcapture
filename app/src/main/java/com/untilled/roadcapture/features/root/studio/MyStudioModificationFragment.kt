package com.untilled.roadcapture.features.root.studio

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.untilled.roadcapture.core.activityresult.ActivityResultFactory
import com.untilled.roadcapture.databinding.FragmentMystudioModificationBinding
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyStudioModificationFragment : Fragment() {
    private var _binding: FragmentMystudioModificationBinding? = null
    private val binding get() = _binding!!
    private var profileImageUri: Uri? = null
    private var backgroundImageUri: Uri? = null
    private val args: MyStudioModificationFragmentArgs by navArgs()

    @Inject
    lateinit var activityResultFactory: ActivityResultFactory<Intent, ActivityResult>

    private val profileContentOnActivityResult: (ActivityResult) -> Unit = {
        if (it.resultCode == Activity.RESULT_OK) {
            profileImageUri = it.data?.data
            binding.imageMystudioModifyProfile.context.apply {
                Glide.with(this)
                    .asBitmap()
                    .load(profileImageUri)
                    .centerCrop()
                    .into(binding.imageMystudioModifyProfile)
            }
        }
    }

    private val getBackgroundContent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            backgroundImageUri = it.data?.data
            binding.imageMystudioModifyBg.context.apply{
                Glide.with(this)
                    .asBitmap()
                    .load(backgroundImageUri)
                    .centerCrop()
                    .into(binding.imageMystudioModifyBg)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMystudioModificationBinding.inflate(inflater,container,false)

        mainActivity().viewModel.setBindingRoot(binding.root)
        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.user = args.user
        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners(){
        binding.imageMystudioModifyCheck.setOnClickListener {
            saveUserInfo()
            Navigation.findNavController(binding.root).popBackStack()
        }
        binding.imageMystudioModifyBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
        binding.imageMystudioModifyBgchange.setOnClickListener {
            pickFromGallery(BACKGROUND)
        }
        binding.imageMystudioModifyProfilechange.setOnClickListener {
            pickFromGallery(PROFILE)
        }
    }

    private fun pickFromGallery(type: Int) {
        val intent = Intent(Intent.ACTION_PICK)
            .setType(MediaStore.Images.Media.CONTENT_TYPE)
            .setType("image/*")

        when(type){
            PROFILE -> activityResultFactory.launch(intent, profileContentOnActivityResult)
            BACKGROUND -> getBackgroundContent.launch(intent)
        }
    }

    private fun saveUserInfo(){
        if(profileImageUri != null)
            args.user?.profileImageUrl = profileImageUri.toString()
        if(backgroundImageUri != null)
            args.user?.backgroundUrl = backgroundImageUri.toString()

        args.user?.introduction = binding.edtMystudioModifyDesc.text.toString()
    }

    private fun initAdapter() {
    }

    companion object{
        const val PROFILE: Int = 1
        const val BACKGROUND: Int = 2
    }
}