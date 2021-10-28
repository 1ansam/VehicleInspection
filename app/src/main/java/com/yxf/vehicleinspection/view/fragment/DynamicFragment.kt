package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentDynamicBinding

class DynamicFragment : BaseBindingFragment<FragmentDynamicBinding>() {
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_dynamicFragment_to_signatureFragment)
        }
    }
}