package com.yxf.vehicleinspection.view.fragment

import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentNavHostBinding


class NavHostFragment : BaseBindingFragment<FragmentNavHostBinding>() {
    companion object{
        const val HOSTNAME_REGISTER = "Register"
        const val HOSTNAME_CHARGE = "Charge"
        const val HOSTNAME_VEHICLE_INSPECTION = "VehicleInspection"
        const val HOSTNAME_DISPATCH = "Dispatch"
        const val HOSTNAME_VERIFY_SIGNATURE = "VerifySignature"
        const val HOSTNAME_VEHICLE_INFOMATION = "VehicleInformation"

    }

    override fun init() {
        this.requireActivity().onBackPressedDispatcher.addCallback(this){
            this@NavHostFragment.requireActivity().finish()
        }
        binding.btnRegisterFunc.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.btnChargeFunc.setOnClickListener {
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment(
                HOSTNAME_CHARGE)
            findNavController().navigate(action)
        }
        binding.btnVehicleInspectionFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnDispatchFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVerifySignatureFunc.setOnClickListener {
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment(
                HOSTNAME_VERIFY_SIGNATURE)
            findNavController().navigate(action)
        }
        binding.btnVehicleInformationFunc.setOnClickListener {
            findNavController().navigate(R.id.vehicleInfoFragment)
        }
    }
}