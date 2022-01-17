package com.untilled.roadcapture.features.login

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.untilled.roadcapture.R


class LoadingDialog(context: Context) : Dialog(context) {

    init {
        initDialog()
        initGif(context)
    }

    private fun initGif(context: Context) {
        val loading = findViewById<ImageView>(R.id.img_dlg_loading)
        Glide.with(context)
            .asGif()
            .load(R.raw.loading)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(loading)
    }

    private fun initDialog() {
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dlg_loading)
    }
}