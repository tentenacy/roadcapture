package com.untilled.roadcapture.features.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {

    protected val compositeDisposable = CompositeDisposable()

    protected val errorObserver = { error: String ->
        if (error.isNotBlank()) Toast.makeText(
            requireContext(),
            "error: $error",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}