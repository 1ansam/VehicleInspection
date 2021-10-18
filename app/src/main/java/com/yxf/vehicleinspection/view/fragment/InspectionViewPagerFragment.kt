package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentInspectionViewPagerBinding
import com.yxf.vehicleinspection.view.adapter.ViewPagerAdapter

class InspectionViewPagerFragment : BaseBindingFragment<FragmentInspectionViewPagerBinding>() {
    override fun init() {
        val title = arrayOf("外观","底盘","动态","联网","唯一性")
        val adapter = ViewPagerAdapter(childFragmentManager,lifecycle)
        binding.vpVehicleQueue.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.vpVehicleQueue.adapter = adapter
        TabLayoutMediator(binding.tlInspectionItem,binding.vpVehicleQueue){
            tab, position -> tab.text = title[position]
        }.attach()
    }

}