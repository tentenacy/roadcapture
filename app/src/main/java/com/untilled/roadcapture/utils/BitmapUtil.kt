package com.untilled.roadcapture.utils

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import java.io.IOException

fun drawCircularBitmap(canvas: Canvas) {
    val path = Path()
    path.fillType = Path.FillType.INVERSE_EVEN_ODD
    path.addCircle(canvas.width / 2f, canvas.height / 2f, canvas.width / 2f, Path.Direction.CW)

    val paint = Paint()
    paint.isAntiAlias = true
    paint.color = Color.TRANSPARENT
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)

    canvas.drawPath(path, paint)
}

fun drawCircularBitmap(bitmap: Bitmap): Bitmap {
    var output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
    var canvas = Canvas(output)

    val color = 0xff424242
    val paint = Paint()
    val rect = Rect(0,0,bitmap.width, bitmap.height)

    paint.isAntiAlias = true
    canvas.drawARGB(0,0,0,0)
    paint.color = color.toInt()

    canvas.drawCircle(
        (bitmap.width/2).toFloat(),
        (bitmap.height/2).toFloat(), (bitmap.width/2).toFloat(), paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    canvas.drawBitmap(bitmap, rect, rect, paint)

    return output
}

fun Uri.getCircularBitmap(context: Context, width: Float, height: Float): Bitmap? {
    var bitmap: Bitmap? = null

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                decoder.setPostProcessor { canvas ->
                    drawCircularBitmap(canvas)
                    return@setPostProcessor PixelFormat.TRANSLUCENT
                }
            }

        } else {
            bitmap = drawCircularBitmap(MediaStore.Images.Media.getBitmap(context.contentResolver, this))
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return Bitmap.createScaledBitmap(bitmap!!, context.getPxFromDp(width), context.getPxFromDp(height), true)
}