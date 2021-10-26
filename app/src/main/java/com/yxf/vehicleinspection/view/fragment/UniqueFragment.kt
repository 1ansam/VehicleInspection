package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentUniqueBinding

class UniqueFragment : BaseBindingFragment<FragmentUniqueBinding>() {
    override fun init() {
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_uniqueFragment_to_inspectionItemFragment)
        }
    }
}