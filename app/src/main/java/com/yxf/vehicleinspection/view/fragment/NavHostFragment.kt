package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.CollectMoney
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.databinding.FragmentNavHostBinding
import com.yxf.vehicleinspection.utils.getStringFromCollectMoney
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.viewModel.SharedViewModel


class NavHostFragment : BaseBindingFragment<FragmentNavHostBinding>() {
    lateinit var bean001 : UserInfoR001Response
    companion object{
        const val HOSTNAME_REGISTER = "Register"
        const val HOSTNAME_APPOINTMENT = "Appointment"
        const val HOSTNAME_CHARGE = "Charge"
        const val HOSTNAME_VEHICLE_INSPECTION = "VehicleInspection"
        const val HOSTNAME_REPLENISH = "Replenish"
        const val HOSTNAME_VERIFY_SIGNATURE = "VerifySignature"
        const val HOSTNAME_VEHICLE_INFORMATION = "VehicleInformation"

    }

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.tvChoiceFunction.text = "${bean001.UserName} 功能选择"
        when{
            bean001.RoleDm.contains("WJY")||bean001.RoleDm.contains("DJY")||bean001.RoleDm.contains("DTJY")||bean001.RoleDm.contains("YCY") ->{
                binding.btnAppointmentAj.visibility = View.GONE
                binding.btnChargeFunc.visibility = View.GONE
                binding.btnVehicleInspectionFunc.visibility = View.VISIBLE
                binding.btnReplenishFunc.visibility = View.VISIBLE
                binding.btnVerifySignatureFunc.visibility = View.GONE
            }
            bean001.RoleDm.contains("SQQZR") ->{
                binding.btnAppointmentAj.visibility = View.GONE
                binding.btnChargeFunc.visibility = View.GONE
                binding.btnVehicleInspectionFunc.visibility = View.GONE
                binding.btnReplenishFunc.visibility = View.GONE
                binding.btnVerifySignatureFunc.visibility = View.VISIBLE
            }
            bean001.RoleDm.contains("XTGLY") ->{
                binding.btnAppointmentAj.visibility = View.VISIBLE
                binding.btnChargeFunc.visibility = View.VISIBLE
                binding.btnVehicleInspectionFunc.visibility = View.VISIBLE
                binding.btnReplenishFunc.visibility = View.VISIBLE
                binding.btnVerifySignatureFunc.visibility = View.VISIBLE
            }
            bean001.RoleDm.contains("DLY") ->{
                binding.btnAppointmentAj.visibility = View.VISIBLE
                binding.btnChargeFunc.visibility = View.VISIBLE
                binding.btnVehicleInspectionFunc.visibility = View.GONE
                binding.btnReplenishFunc.visibility = View.GONE
                binding.btnVerifySignatureFunc.visibility = View.GONE
            }
        }
        val sharedViewModel : SharedViewModel by activityViewModels()
        binding.btnRegisterFunc.setOnClickListener {

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
        binding.btnReplenishFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_REPLENISH)
            val action = NavHostFragmentDirections.actionNavHostFragmentToVehicleQueueFragment()
            findNavController().navigate(action)
        }
        binding.btnVerifySignatureFunc.setOnClickListener {
            sharedViewModel.setHostName(HOSTNAME_VERIFY_SIGNATURE)
            val action = NavHostFragmentDirections.actionNavHostFragmentToModerationQueueFragment()
            findNavController().navigate(action)
        }

    }
}