package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.SaveSignatureW006Request
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.view.PaintView
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModelFactory
import java.util.*


class SignatureFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    val signatureViewModel by viewModels<SignatureViewModel> { SignatureViewModelFactory((requireActivity().application as MyApp).signatureRepository) }
    val args : SignatureFragmentArgs by navArgs()
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
            binding.pbSignature.visibility = View.VISIBLE
            val saveSignatureW006Request = SaveSignatureW006Request(0,args.bean005!!.Lsh,args.bean006!!.Jccs,args.bean005!!.Hphm,mPaintView.base64,
                date2String(Date(),"yyyy-MM-dd HH:mm:ss"),args.bean006!!.Jcxm,
                SharedP.instance.getString("username","")!!,"1","1")
            signatureViewModel.postSignature(saveSignatureW006Request).observe(this){
                if (it){
                    binding.pbSignature.visibility = View.GONE
                    sharedViewModel.hostName.observe(this) { hostName ->
                        when {
                            hostName.equals(NavHostFragment.HOSTNAME_VERIFY_SIGNATURE)
                                    || hostName.equals(NavHostFragment.HOSTNAME_DISPATCH)
                                    || hostName.equals(NavHostFragment.HOSTNAME_CHARGE) -> {
                                val action =
                                    SignatureFragmentDirections.actionSignatureFragmentPopIncludingVehicleQueueFragment()

                                findNavController().navigate(action)
                            }
                            hostName.equals(NavHostFragment.HOSTNAME_VEHICLE_INSPECTION) -> {
                                val action =
                                    SignatureFragmentDirections.actionSignatureFragmentPopIncludingInspectionItemFragment()
                                findNavController().navigate(action)
                            }
                            hostName.equals(NavHostFragment.HOSTNAME_REGISTER) -> {
                                val action =
                                    SignatureFragmentDirections.actionSignatureFragmentPopIncludingRegisterFragment()
                                findNavController().navigate(action)
                            }

                        }

                    }
                }else{
                    binding.pbSignature.visibility = View.GONE
                }
            }

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