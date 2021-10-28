package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentUniqueBinding

class UniqueFragment : BaseBindingFragment<FragmentUniqueBinding>() {
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_uniqueFragment_to_inspectionItemFragment)
        }
    }
}