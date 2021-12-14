package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentNavHostBinding
import com.yxf.vehicleinspection.viewModel.SharedViewModel


class NavHostFragment : BaseBindingFragment<FragmentNavHostBinding>() {
    companion object{
        const val HOSTNAME_REGISTER = "Register"
        const val HOSTNAME_APPOINTMENT = "Appointment"
        const val HOSTNAME_CHARGE = "Charge"
        const val HOSTNAME_VEHICLE_INSPECTION = "VehicleInspection"
        const val HOSTNAME_DISPATCH = "Dispatch"
        const val HOSTNAME_VERIFY_SIGNATURE = "VerifySignature"
        const val HOSTNAME_VEHICLE_INFORMATION = "VehicleInformation"

    }

    override fun init() {
//        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val sharedViewModel : SharedViewModel by activityViewModels()
        binding.btnRegisterFunc.setOnClickListener {
//            val action = NavHostFragmentDirections.actionNavHostFragmentToRegisterFragment()
//            findNavController().navigate(action)
        }
        binding.btnAppointmentAj.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_APPOINTMENT)
            val action = NavHostFragmentDirections.actionNavHostFragmentToAppointmentAjFragment()
            findNavController().navigate(action)
        }
        binding.btnChargeFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_CHARGE)
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment()
            findNavController().navigate(action)
        }
        binding.btnVehicleInspectionFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_VEHICLE_INSPECTION)
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment()
            findNavController().navigate(action)
        }
        binding.btnDispatchFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_DISPATCH)
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment()
            findNavController().navigate(action)
        }
        binding.btnVerifySignatureFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_VERIFY_SIGNATURE)
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment()
            findNavController().navigate(action)
        }
        binding.btnVehicleInformationFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_VEHICLE_INFORMATION)

            findNavController().navigate(R.id.action_navHostFragment_to_vehicleInfoFragment2)
        }
    }
}