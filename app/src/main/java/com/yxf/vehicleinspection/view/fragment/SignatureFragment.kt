package com.yxf.vehicleinspection.view.fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.view.activity.PaintView


class SignatureFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        this.requireActivity().onBackPressedDispatcher.addCallback(this){
            this@SignatureFragment.findNavController().navigate(R.id.vehicleQueueFragment)
        }
        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        val mPaintView = PaintView(this.requireContext(),screenWidth,screenHeight)
        binding.tablet.addView(mPaintView)
        mPaintView.requestFocus()
        binding.back.setOnClickListener {
            it.findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.clear.setOnClickListener {
            mPaintView.clear()
        }
        binding.commit.setOnClickListener {
            val header = "data:image/png;base64"
            val base64 = "$header,${mPaintView.base64}"
            val bundle = Bundle()
            bundle.putString("base64",base64)
            it.findNavController().navigate(R.id.vehicleQueueFragment)
        }


    }
    fun getScreenWidth(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = this.requireActivity().window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            return windowMetrics.bounds.width() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            this.requireActivity().window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            return mDisplayMetrics.widthPixels
        }
    }

    fun getScreenHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = this.requireActivity().window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            return windowMetrics.bounds.height() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            this.requireActivity().window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            return mDisplayMetrics.heightPixels
        }
    }

}