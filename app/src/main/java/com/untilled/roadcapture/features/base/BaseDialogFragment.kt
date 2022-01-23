package com.untilled.roadcapture.features.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.untilled.roadcapture.R

open class BaseDialogFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.CustomDialogFragment)
    }

    protected val dismissOnClickListener: (View?) -> Unit = {
        dismiss()
    }
}