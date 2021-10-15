package com.yxf.vehicleinspection.view.fragment

import android.os.Debug
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentChargeBinding

class ChargeFragment : BaseBindingFragment<FragmentChargeBinding>() {
    override fun init() {
        val str = "https://baidu.com"
        val encode = BarcodeEncoder()
        val bitmap = encode.encodeBitmap(str, BarcodeFormat.QR_CODE,800,800)
        binding.ivQRCode.setImageBitmap(bitmap)
    }

}