package com.untilled.roadcapture.features.root.search

import android.text.InputFilter
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.untilled.roadcapture.utils.extension.getPxFromDp

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
}