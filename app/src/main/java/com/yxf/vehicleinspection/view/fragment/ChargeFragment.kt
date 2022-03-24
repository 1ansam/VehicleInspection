package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.databinding.FragmentChargeBinding
import com.yxf.vehicleinspection.utils.getSignFromCollectMoney
import com.yxf.vehicleinspection.utils.getStringFromCollectMoney
import com.yxf.vehicleinspection.viewModel.ChargeViewModel
import com.yxf.vehicleinspection.viewModel.ChargeViewModelFactory
import com.yxf.vehicleinspection.viewModel.VehicleAllInfoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleAllInfoViewModelFactory
import java.util.*
import kotlin.concurrent.timerTask

class ChargeFragment : BaseBindingFragment<FragmentChargeBinding>() {
    val timer = Timer()
    val args : ChargeFragmentArgs by navArgs()
    private val chargeViewModel by
    viewModels<ChargeViewModel> { ChargeViewModelFactory(
        (requireActivity().application as MyApp).chargeRepository)
    }
    private val vehicleAllInfoViewModel by viewModels<VehicleAllInfoViewModel> {
        VehicleAllInfoViewModelFactory((requireActivity().application as MyApp).vehicleAllInfoRepository)
    }
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        var url  = args.c1
        var collectMoney = args.collectMoney
        val sign = getSignFromCollectMoney(collectMoney)
        val wbean004 = args.wbean004
        val bean002 = args.bean002
        binding.tvAmount.text = resources.getString(R.string.amount_,collectMoney.amt/100)
        binding.tvOid.text = resources.getString(R.string.oid_,collectMoney.oid)
        binding.tvHphm.text = resources.getString(R.string.hphm_,bean002.Hphm)
        wbean004.chargeOrder.Sign = sign
        collectMoney = CollectMoney(collectMoney.appid,collectMoney.c,collectMoney.oid,collectMoney.amt,collectMoney.trxreserve,sign,collectMoney.key)
        url += getStringFromCollectMoney(collectMoney)
        val encode = BarcodeEncoder()
        val bitmap = encode.encodeBitmap(url, BarcodeFormat.QR_CODE,500,500)
        binding.ivQRCode.setImageBitmap(bitmap)
        val handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                val liveData = msg.obj as LiveData<Boolean>
                liveData.observe(this@ChargeFragment){
                    if (it){
                        timer.cancel()
                        Toast.makeText(MyApp.context, "付款成功", Toast.LENGTH_SHORT).show()
                        chargeViewModel.postChargePayment(wbean004).observe(this@ChargeFragment){
                                if (it){
                                    Snackbar.make(this@ChargeFragment.requireView(),"上传付款信息成功",Snackbar.LENGTH_SHORT).show()
                                    vehicleAllInfoViewModel.getVehicleAllInfo(args.bean002.Ajlsh,args.bean002.Hjlsh).observe(this@ChargeFragment){
                                        if (it.isNotEmpty()){
                                            val action = ChargeFragmentDirections.actionChargeFragmentToInvoiceFragment(it[0],wbean004)
                                            findNavController().navigate(action)
                                        }else{
                                            Toast.makeText(
                                                this@ChargeFragment.requireContext(),
                                                "未获取到车辆信息",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }
                                }
                        }

                    }

                }
            }
        }
        binding.btnInvoice.setOnClickListener {
            chargeViewModel.postChargePayment(wbean004).observe(this@ChargeFragment){
                if (it){
                    Snackbar.make(this@ChargeFragment.requireView(),"上传付款信息成功",Snackbar.LENGTH_SHORT).show()
                    vehicleAllInfoViewModel.getVehicleAllInfo(args.bean002.Ajlsh,args.bean002.Hjlsh).observe(this@ChargeFragment){
                        if (it.isNotEmpty()){
                            val action = ChargeFragmentDirections.actionChargeFragmentToInvoiceFragment(it[0],wbean004)
                            findNavController().navigate(action)
                        }else{
                            Toast.makeText(
                                this@ChargeFragment.requireContext(),
                                "未获取到车辆信息",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
        timer.schedule(timerTask {
            val message = Message()
            message.obj = chargeViewModel.getChargeStatus(collectMoney.oid)
            handler.sendMessage(message)
        },1000,3000)

    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

}