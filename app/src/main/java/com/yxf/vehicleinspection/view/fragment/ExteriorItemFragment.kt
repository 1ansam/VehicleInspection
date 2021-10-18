package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentExteriorItemBinding

class ExteriorItemFragment : BaseBindingFragment<FragmentExteriorItemBinding>() {
    override fun init() {
        //提交外观查验
        binding.btnSubmitExterior.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}