package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleInfoBinding

class VehicleInfoFragment : BaseBindingFragment<FragmentVehicleInfoBinding>() {
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}