package com.yxf.vehicleinspection.view.fragment

import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentChassisBinding

class ChassisFragment : BaseBindingFragment<FragmentChassisBinding>() {
    override fun init() {
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_chassisFragment_to_signatureFragment)
        }
    }
}