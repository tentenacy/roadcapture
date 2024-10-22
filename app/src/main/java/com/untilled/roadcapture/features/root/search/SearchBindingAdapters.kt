package com.untilled.roadcapture.features.root.search

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.untilled.roadcapture.R
import com.untilled.roadcapture.utils.getPxFromDp

object SearchBindingAdapters {

    @JvmStatic
    @BindingAdapter("searchEditText")
    fun setSearchEditText(view: EditText, any: Any?) {
        val context = view.context
        context.apply {
            view.minHeight = getPxFromDp(0.0f)
            view.setPadding(0, getPxFromDp(12.0f), 0, getPxFromDp(12.0f))
            view.maxLines = 1
            view.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(15))
            view.setBackgroundColor(context.getColor(android.R.color.transparent))
        }
    }

    @JvmStatic
    @BindingAdapter("titleSearchThumbnailImage")
    fun setTitleSearchThumbnailImage(view: ShapeableImageView, url: String) {
        view.context.apply {
            val radius = resources.getDimension(R.dimen.studio_album_corner_radius)
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

    @JvmStatic
    @BindingAdapter("titleSearchProfileImage")
    fun setTitleSearchProfileImage(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .circleCrop()
            .into(view)
    }

    /********/
/*
    //BindingAdapter (Setter 역할)
    @JvmStatic
    @BindingAdapter("android:text")
    fun getSearchText(view: EditText, text: String?){
        if (view.text.toString() != text) view.setText(text)
    }

    //InverseBindingListener (InverseBindingAdapter 실행 역할)
    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun setSearchTextWatcher(view: EditText, listener: InverseBindingListener?){

        view.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onChange()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    //InverseBindingAdapter (Getter 역할)
    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
    fun getSearchText(view: EditText): String? {
        return view.text.toString()
    }
*/

}