package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.request.SaveSignatureW006Request
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentSignatureBinding
import com.yxf.vehicleinspection.utils.date2String
import com.yxf.vehicleinspection.utils.getScreenHeight
import com.yxf.vehicleinspection.utils.getScreenWidth
import com.yxf.vehicleinspection.view.PaintView
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModel
import com.yxf.vehicleinspection.viewModel.SignatureViewModelFactory
import java.util.*


class SignatureFragment : BaseBindingFragment<FragmentSignatureBinding>() {
    private val signatureViewModel by viewModels<SignatureViewModel> { SignatureViewModelFactory((requireActivity().application as MyApp).signatureRepository) }
    val args : SignatureFragmentArgs by navArgs()
    lateinit var bean001 : UserInfoR001Response
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

        }
        binding.btnClear.setOnClickListener {
            mPaintView.clear()
        }
        binding.btnCommit.setOnClickListener {
            binding.pbSignature.visibility = View.VISIBLE
            val saveSignatureW006Request = SaveSignatureW006Request(0,args.bean005.Hphm,mPaintView.base64,
                date2String(Date(),"yyyy-MM-dd HH:mm:ss"),args.jcxm,
                bean001.TrueName,args.ajywlb,args.hjywlb,
                args.bean002.Ajlsh,args.bean002.Hjlsh,
                args.bean002.Ajjccs,args.bean002.Hjjccs)
            signatureViewModel.postSignature(saveSignatureW006Request).observe(this){
                if (it){
                    binding.pbSignature.visibility = View.GONE
                    sharedViewModel.hostName.observe(this) { hostName ->
                        when {
                            hostName.equals(NavHostFragment.HOSTNAME_VERIFY_SIGNATURE)
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
                            hostName.equals(NavHostFragment.HOSTNAME_APPOINTMENT) -> {
                                val action =
                                    SignatureFragmentDirections.actionSignatureFragmentPopIncludingAppointmentAjFragment()
                                findNavController().navigate(action)
                            }
                            hostName.equals(NavHostFragment.HOSTNAME_REPLENISH) -> {
                                Toast.makeText(this.context, "??????????????????", Toast.LENGTH_SHORT).show()
                                val action = SignatureFragmentDirections.actionSignatureFragmentPopIncludingReplenishFragment()
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


}