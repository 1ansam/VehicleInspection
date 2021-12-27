package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.SaveSignatureW006Request
import com.yxf.vehicleinspection.bean.request.SaveVerifyInfoW013Request
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.singleton.SharedP
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.getScreenHeight
import com.yxf.vehicleinspection.utils.getScreenWidth
import com.yxf.vehicleinspection.view.PaintView
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModelFactory
import java.util.*


class SignatureVerifyFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    val signatureViewModel by viewModels<SignatureViewModel> { SignatureViewModelFactory((requireActivity().application as MyApp).signatureRepository) }
    lateinit var bean001 : UserInfoR001Response
    val args : SignatureVerifyFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        val screenWidth = getScreenWidth(this.requireActivity())
        val screenHeight = getScreenHeight(this.requireActivity())
        val mPaintView = PaintView(this.requireContext(),screenWidth,screenHeight)
        binding.tablet.addView(mPaintView)
        mPaintView.requestFocus()
        val sharedViewModel : SharedViewModel by activityViewModels()
        binding.backFromSignature.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnClear.setOnClickListener {
            mPaintView.clear()
        }
        binding.btnCommit.setOnClickListener {
            binding.pbSignature.visibility = View.VISIBLE
            signatureViewModel.postVerifyInfo(SaveVerifyInfoW013Request(
                args.bean013.Ajlsh,
                args.bean013.Hjlsh,
                args.bean013.Ajywlb,
                args.bean013.Hjywlb,
                args.bean013.Ajjccs,
                args.bean013.Hjjccs,
                bean001.TrueName,
                bean001.ID,
                args.bean013.Ajjccs.toString(),
                args.shjg.toString(),
                "",
                mPaintView.base64
            )).observe(this){
                if (it){
                    binding.pbSignature.visibility = View.GONE
                    val action = SignatureVerifyFragmentDirections.actionSignatureVerifyFragmentPopIncludingModerationQueueFragment()
                    findNavController().navigate(action)
                }
            }
        }


    }


}