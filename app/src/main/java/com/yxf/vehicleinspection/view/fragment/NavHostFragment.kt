package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentNavHostBinding


class NavHostFragment : BaseBindingFragment<FragmentNavHostBinding>() {

    override fun init() {
        val bundle = Bundle()
        this.requireActivity().onBackPressedDispatcher.addCallback(this){
            this@NavHostFragment.requireActivity().finish()
        }
        binding.btnRegisterFunc.setOnClickListener {
            bundle.putString("host","register")
            findNavController().navigate(R.id.registerFragment,bundle)
        }
        binding.btnChargeFunc.setOnClickListener {
            bundle.putString("host","charge")
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnPersonInspectionItemFunc.setOnClickListener {
            bundle.putString("host","personInspection")
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnDispatchFunc.setOnClickListener {
            bundle.putString("host","dispatch")
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVerifySignatureFunc.setOnClickListener {
            bundle.putString("host","verifySignature")
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVehicleInformationFunc.setOnClickListener {
            bundle.putString("host","vehicleInformation")
            findNavController().navigate(R.id.vehicleInfoFragment)
        }
    }
}