package com.untilled.roadcapture.features.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    protected val errorObserver = { error: String ->
        if (error.isNotBlank()) Toast.makeText(
            requireContext(),
            "error: $error",
            Toast.LENGTH_SHORT
        ).show()
    }
}