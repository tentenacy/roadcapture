package com.untilled.roadcapture.features.root.albums

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.dateToSnsFormat
import com.untilled.roadcapture.utils.getPxFromDp
import de.hdodenhof.circleimageview.CircleImageView
import java.time.LocalDateTime


object AlbumsBindingAdapters {

    @JvmStatic
    @BindingAdapter("albumThumbnailImage")
    fun setAlbumThumbnailImage(view: ShapeableImageView, url: String) {
        view.context.apply {
            val radius = resources.getDimension(R.dimen.album_corner_radius)
            val shapeAppearanceModel = view.shapeAppearanceModel.toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()

            view.shapeAppearanceModel = shapeAppearanceModel

            Glide.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("pictureViewerBackgroundImage")
    fun setPictureViewerBackgroundImage(view: ImageView, url: String){
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("albumProfileImage")
    fun setAlbumProfileImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .circleCrop()
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("albumOverlayUsername")
    fun setAlbumOverlayText(view: TextView, username: String?) {
        // TODO: createdDate를 지정된 형식으로 변환
        view.text = "$username · "

    }

    @JvmStatic
    @BindingAdapter("adjustConstraintEmptyDesc")
    fun adjustConstraint(view: ConstraintLayout, description: String) {
        if (description.isBlank()) {
            val constraints = ConstraintSet()
            constraints.clone(view)
            constraints.connect(
                R.id.text_ialbums_title,
                ConstraintSet.BOTTOM,
                view.id,
                ConstraintSet.BOTTOM,
                view.context.getPxFromDp(48f)
            )
            constraints.applyTo(view)
        } else{
            val constraints = ConstraintSet()
            constraints.clone(view)
            constraints.clear(R.id.text_ialbums_title,ConstraintSet.BOTTOM)
            constraints.applyTo(view)
        }
    }

    @JvmStatic
    @BindingAdapter("FollowingAlbumProfileImage")
    fun setFollowingAlbumProfileImage(view: CircleImageView, url: String){
        view.context.apply {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("FollowingAlbumUpdate")
    fun setFollowingAlbumUpdate(view: CircleImageView, update: Int){
        if(update == 1)
            view.setImageResource(R.color.secondaryColor)
        else
            view.setImageResource(R.color.lightGray)
    }

    @JvmStatic
    @BindingAdapter("SelectedStatus")
    fun setSelectedStatus(view: View, clicked: Boolean){
        if(clicked) view.visibility = View.INVISIBLE
        else view.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @BindingAdapter("DateToSnsFormat")
    fun setDateToSnsFormat(view: TextView, date: LocalDateTime){
        view.text = dateToSnsFormat(date)
    }
}