package com.yxf.vehicleinspection.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream

/**
 *   author:yxf
 *   time:2021/10/12
 */
object ImageUtil {
    fun bitmap2Base64(bitmap: Bitmap) : String{

        var baos : ByteArrayOutputStream? = null
        try {
            baos = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
            baos.flush()
            baos.close()
            val bitmapBytes = baos.toByteArray()
            return Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
        }catch (e : Exception){
            e.message
        }
        finally {
            baos?.flush()
            baos?.close()
        }
        return ""
    }
    fun base642Bitmap(base64: String?) : Bitmap?{
            val bytes = Base64.decode(base64,Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }
    fun getBitmapFromDrawable(drawable : Drawable) : Bitmap{
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0,0,drawable.intrinsicWidth,drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }
}