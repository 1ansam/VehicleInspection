package com.yxf.vehicleinspection.view.fragment

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentNavHostBinding


class NavHostFragment : BaseBindingFragment<FragmentNavHostBinding>() {


    override fun init() {


        this.requireActivity().onBackPressedDispatcher.addCallback(this){
            this@NavHostFragment.requireActivity().finish()
        }
        binding.btnRegisterFunc.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnChargeFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnInspectionItemFunc.setOnClickListener {
            findNavController().navigate(R.id.inspectionViewPagerFragment)
        }
        binding.btnDispatchFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVerifySignatureFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVehicleInformationFunc.setOnClickListener {
            findNavController().navigate(R.id.inspectionViewPagerFragment)
        }
    }
}