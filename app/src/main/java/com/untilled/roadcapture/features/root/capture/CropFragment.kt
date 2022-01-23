package com.untilled.roadcapture.features.root.capture

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.entity.Picture
import com.untilled.roadcapture.databinding.FragmentCropBinding
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.navigateToPictureEditor
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CropFragment : Fragment() {
    private var _binding: FragmentCropBinding? = null
    val binding get() = _binding!!
    private lateinit var imageUri: Uri

    private var uCropFragment: UCropFragment? = null
    var mShowLoader = false

    private var mToolbarTitle: String? = null

    // Enables dynamic coloring
    private var mToolbarColor = 0
    private var mStatusBarColor = 0
    private var mToolbarWidgetColor = 0

    @DrawableRes
    private var mToolbarCancelDrawable = 0

    @DrawableRes
    private var mToolbarCropDrawable = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCropBinding.inflate(inflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        setHasOptionsMenu(true) // fragment 에서 optionMenu 생성 허용

        val args: CropFragmentArgs by navArgs()
        imageUri = args.imageUri.toUri()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCrop(imageUri)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startCrop(uri: Uri) {
        // cache 디렉토리에 crop image 저장
        var uCrop: UCrop = UCrop.of(uri, Uri.fromFile(File.createTempFile("crop_", ".jpg", requireContext().cacheDir)))

        uCrop = uCrop.withAspectRatio(1f, 1f)
        uCrop = advancedConfig(uCrop)

        setupFragment(uCrop)
    }

    private fun advancedConfig(uCrop: UCrop): UCrop {
        var options = UCrop.Options()

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
        options.setCompressionQuality(100)

        return uCrop.withOptions(options)
    }

    private fun setupFragment(uCrop: UCrop) {
        uCropFragment = uCrop.getFragment(uCrop.getIntent(requireContext()).extras)
        mainActivity().supportFragmentManager.beginTransaction()
            .add(R.id.frame_crop_container_content, uCropFragment!!, UCropFragment.TAG)
            .commitAllowingStateLoss()

        setupViews(uCrop.getIntent(requireContext()).extras!!)
    }

    private fun setupViews(args: Bundle) {
        mStatusBarColor = args.getInt(
            UCrop.Options.EXTRA_STATUS_BAR_COLOR,
            ContextCompat.getColor(requireContext(), R.color.ucrop_color_statusbar)
        )
        mToolbarColor = args.getInt(
            UCrop.Options.EXTRA_TOOL_BAR_COLOR,
            ContextCompat.getColor(requireContext(), R.color.ucrop_color_toolbar)
        )
        mToolbarCancelDrawable =
            args.getInt(UCrop.Options.EXTRA_UCROP_WIDGET_CANCEL_DRAWABLE, R.drawable.ucrop_ic_cross)
        mToolbarCropDrawable =
            args.getInt(UCrop.Options.EXTRA_UCROP_WIDGET_CROP_DRAWABLE, R.drawable.ucrop_ic_done)
        mToolbarWidgetColor = args.getInt(
            UCrop.Options.EXTRA_UCROP_WIDGET_COLOR_TOOLBAR,
            ContextCompat.getColor(requireContext(), R.color.ucrop_color_toolbar_widget)
        )
        mToolbarTitle = args.getString(UCrop.Options.EXTRA_UCROP_TITLE_TEXT_TOOLBAR)
        mToolbarTitle =
            if (mToolbarTitle != null) mToolbarTitle else resources.getString(R.string.ucrop_label_edit_photo)

        setupAppBar()
    }

    private fun setupAppBar() {
        // Set all of the Toolbar coloring
        binding.toolbarCrop.setBackgroundColor(mToolbarColor)
        binding.toolbarCrop.setTitleTextColor(mToolbarWidgetColor)
        binding.toolbarCrop.visibility = View.VISIBLE
        binding.textCropTitle.setTextColor(mToolbarWidgetColor)
        binding.textCropTitle.text = mToolbarTitle

        // Color buttons inside the Toolbar
        val stateButtonDrawable =
            ContextCompat.getDrawable(requireContext(), mToolbarCancelDrawable)
        if (stateButtonDrawable != null) {
            stateButtonDrawable.mutate()
            stateButtonDrawable.setColorFilter(mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP)
            binding.toolbarCrop.navigationIcon = stateButtonDrawable
        }

        mainActivity().setSupportActionBar(binding.toolbarCrop)
        mainActivity().supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        mainActivity().menuInflater.inflate(R.menu.ucrop_menu_activity, menu)

        // Change crop & loader menu icons color to match the rest of the UI colors
        val menuItemLoader = menu.findItem(R.id.menu_loader)
        val menuItemLoaderIcon = menuItemLoader.icon
        if (menuItemLoaderIcon != null) {
            try {
                menuItemLoaderIcon.mutate()
                menuItemLoaderIcon.setColorFilter(mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP)
                menuItemLoader.icon = menuItemLoaderIcon
            } catch (e: IllegalStateException) {
                Log.i(
                    this.javaClass.name,
                    java.lang.String.format(
                        "%s - %s",
                        e.message,
                        getString(R.string.ucrop_mutate_exception_hint)
                    )
                )
            }
            (menuItemLoader.icon as Animatable).start()
        }
        val menuItemCrop = menu.findItem(R.id.menu_crop)
        val menuItemCropIcon = ContextCompat.getDrawable(
            requireContext(),
            if (mToolbarCropDrawable == 0) R.drawable.ucrop_ic_done else mToolbarCropDrawable
        )
        if (menuItemCropIcon != null) {
            menuItemCropIcon.mutate()
            menuItemCropIcon.setColorFilter(mToolbarWidgetColor, PorterDuff.Mode.SRC_ATOP)
            menuItemCrop.icon = menuItemCropIcon
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_crop).isVisible = !mShowLoader
        menu.findItem(R.id.menu_loader).isVisible = mShowLoader
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_crop) {
            if (uCropFragment != null && uCropFragment!!.isAdded)
                uCropFragment!!.cropAndSaveImage()
        } else if (item.itemId == android.R.id.home) {
            removeFragmentFromScreen()
            findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    fun removeFragmentFromScreen() {
        mainActivity().supportFragmentManager.beginTransaction()
            .remove(uCropFragment!!)
            .commit()
        binding.toolbarCrop.visibility = View.GONE
    }

    fun handleCropResult(result: Intent) {
        val resultUri = UCrop.getOutput(result)
        if (resultUri != null) {
            // crop 성공하였으므로 crop한 이미지 uri 전달
            navigateToPictureEditor(Picture(imageUrl = resultUri.toString()))
        } else {
            Toast.makeText(requireContext(), "실패", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleCropError(result: Intent) {
        val cropError = UCrop.getError(result)
        if (cropError != null) {
            Toast.makeText(requireContext(), cropError.message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "예상치 못한 에러 발생", Toast.LENGTH_SHORT)
                .show()
        }
    }
    fun invalidateOptionsMenu(){
        mainActivity().supportInvalidateOptionsMenu()
    }
}