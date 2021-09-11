package com.untilled.roadcapture.features.root.capture

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import com.naver.maps.map.overlay.InfoWindow
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ItemCaptureInfowindowBinding

class CaptureInfoWindowAdapter(val _context: Context, val _parent : ViewGroup, val uri : String)
    : InfoWindow.DefaultViewAdapter(_context) {

    override fun getContentView(p0: InfoWindow): View {
        val view : View = LayoutInflater.from(_context).inflate(R.layout.item_capture_infowindow, _parent, false)
        val imageView : ImageView = view.findViewById(R.id.imageview_item_capture_info_window)

        imageView.setImageURI(uri.toUri())

        return view
    }
}
