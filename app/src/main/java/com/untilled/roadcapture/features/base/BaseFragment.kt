package com.untilled.roadcapture.features.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.features.login.LoadingDialog

open class BaseFragment: Fragment() {

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    protected val isLoadingObserver = { isLoading: Boolean ->
        if (isLoading) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }

    protected val errorObserver = { error: String ->
        if (error.isNotBlank()) Toast.makeText(
            requireContext(),
            "error: $error",
            Toast.LENGTH_SHORT
        ).show()
    }
}