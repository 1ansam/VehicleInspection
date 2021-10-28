package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowInsets
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.view.PaintView
import com.yxf.vehicleinspection.viewModel.SharedViewModel


class SignatureFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val screenWidth = getScreenWidth()
        val screenHeight = getScreenHeight()
        val mPaintView = PaintView(this.requireContext(),screenWidth,screenHeight)
        binding.tablet.addView(mPaintView)
        mPaintView.requestFocus()
        val sharedViewModel : SharedViewModel by activityViewModels()
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
            sharedViewModel.hostName.observe(this,{
                when {
                    it.equals(NavHostFragment.HOSTNAME_VERIFY_SIGNATURE)
                            ||it.equals(NavHostFragment.HOSTNAME_DISPATCH)
                            ||it.equals(NavHostFragment.HOSTNAME_CHARGE)-> {
                        val action = SignatureFragmentDirections.actionSignatureFragmentPopIncludingVehicleQueueFragment()
                        findNavController().navigate(action)
                    }
                    it.equals(NavHostFragment.HOSTNAME_VEHICLE_INSPECTION)-> {
                        val action = SignatureFragmentDirections.actionSignatureFragmentPopIncludingInspectionItemFragment()
                        findNavController().navigate(action)
                    }
                    it.equals(NavHostFragment.HOSTNAME_REGISTER) -> {
                        val action = SignatureFragmentDirections.actionSignatureFragmentPopIncludingRegisterFragment()
                        findNavController().navigate(action)
                    }

                }

            })
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