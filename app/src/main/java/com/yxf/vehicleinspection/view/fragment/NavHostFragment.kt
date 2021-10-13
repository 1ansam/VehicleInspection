package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.btnPersonInspectionItem.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnVerifySignature.setOnClickListener {
            findNavController().navigate(R.id.vehicleQueueFragment)
        }
    }
}