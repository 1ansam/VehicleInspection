package com.yxf.vehicleinspection.view.activity

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivitySignatureBinding
import java.io.ByteArrayOutputStream

class SignatureActivity : BaseBindingActivity<ActivitySignatureBinding>() {
    private val context : Context = this
    override fun init() {

        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        val mPaintView = PaintView(context,screenWidth,screenHeight)
        binding.tablet.addView(mPaintView)
        mPaintView.requestFocus()
        binding.back.setOnClickListener {
            finish()
        }
        binding.clear.setOnClickListener {
            mPaintView.clear()
        }
        binding.commit.setOnClickListener {
            val base64 = mPaintView.base64
            val header = "data:image/png;base64"
            Log.e("Base64", "{$header,$base64}", )
        }

    }
    fun getScreenWidth(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            return windowMetrics.bounds.width() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            return mDisplayMetrics.widthPixels
        }
    }

    fun getScreenHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            return windowMetrics.bounds.height() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            return mDisplayMetrics.heightPixels
        }
    }


}