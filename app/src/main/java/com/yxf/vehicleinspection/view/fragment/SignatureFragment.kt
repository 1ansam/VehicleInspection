package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.view.PaintView


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
        binding.backFromSignature.setOnClickListener {

        }
        binding.btnClear.setOnClickListener {
            mPaintView.clear()
        }
        binding.btnCommit.setOnClickListener {
            val header = "data:image/png;base64"
            val base64 = "$header,${mPaintView.base64}"
            val bundle = Bundle()
            bundle.putString("base64",base64)
            it.findNavController().navigate(R.id.action_signatureFragment_pop_including_inspectionItemFragment)
        }


    }
    private fun getScreenWidth(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = this.requireActivity().window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            this.requireActivity().window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            mDisplayMetrics.widthPixels
        }
    }

    private fun getScreenHeight(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val windowMetrics = this.requireActivity().window.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.left - insets.right
        }else{
            val mDisplayMetrics = DisplayMetrics()
            this.requireActivity().window.windowManager.defaultDisplay.getMetrics(mDisplayMetrics)
            mDisplayMetrics.heightPixels
        }
    }

}