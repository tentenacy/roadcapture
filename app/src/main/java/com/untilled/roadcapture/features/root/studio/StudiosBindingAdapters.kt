package com.untilled.roadcapture.features.root.studio

import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R
import de.hdodenhof.circleimageview.CircleImageView

object StudiosBindingAdapters {

    @JvmStatic
    @BindingAdapter("studioThumbnailImage")
    fun setStudioThumbnailImage(view: ShapeableImageView, url: String?) {
        url?.let {
            view.context.apply {
                val radius = resources.getDimension(R.dimen.studio_album_corner_radius)
                val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED,radius)
                    .build()
                view.shapeAppearanceModel = shapeAppearanceModel

                Glide.with(this)
                    .asBitmap()
                    .load(it)
                    .centerCrop()
                    .into(view)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("studioProfileImage")
    fun setStudioProfileImage(view: CircleImageView, url: String?){
        if(url == null) Log.d("Test","null")
        else Log.d("Test",url)
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("studioBackgroundImage")
    fun setStudioBackgroundImage(view: ImageView, url: String?){
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("FollowedStatus")
    fun setFollowedStatus(view: Button, followed: Boolean){
        if(followed){
            view.text = "언팔로우"
        } else{
            view.text = "팔로우"
        }
    }

    @JvmStatic
    @BindingAdapter("studioPlaceImage")
    fun setStudioPlaceImage(view: ShapeableImageView, url: String){
        view.context.apply {
            val radius = resources.getDimension(R.dimen.studio_place_corner_radius)
            val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build()
            view.shapeAppearanceModel = shapeAppearanceModel

            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }
}