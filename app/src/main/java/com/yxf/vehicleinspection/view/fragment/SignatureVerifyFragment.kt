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
import com.yxf.vehicleinspection.utils.getScreenHeight
import com.yxf.vehicleinspection.utils.getScreenWidth
import com.yxf.vehicleinspection.view.PaintView
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModelFactory
import java.util.*


class SignatureVerifyFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    val signatureViewModel by viewModels<SignatureViewModel> { SignatureViewModelFactory((requireActivity().application as MyApp).signatureRepository) }
    val args : SignatureFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val screenWidth = getScreenWidth(this.requireActivity())
        val screenHeight = getScreenHeight(this.requireActivity())
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

        }


    }


}