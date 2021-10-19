package com.yxf.vehicleinspection.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.InspectionItem
import com.yxf.vehicleinspection.bean.VehicleInformation
import com.yxf.vehicleinspection.databinding.FragmentInspectionItemBinding
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleInformationAdapter

class InspectionItemFragment : BaseBindingFragment<FragmentInspectionItemBinding>() {
    lateinit var vehicleInformationAdapter : VehicleInformationAdapter
    lateinit var inspectionItemAdapter : InspectionItemAdapter
    override fun init() {
        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvInspectionItem.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleInformationAdapter()
        inspectionItemAdapter = InspectionItemAdapter()
        binding.rvVehicleInformation.adapter = vehicleInformationAdapter
        binding.rvInspectionItem.adapter = inspectionItemAdapter
        getData()
    }

    private fun getData() {
        val vehicleInformationList = ArrayList<VehicleInformation>()
        vehicleInformationList.add(VehicleInformation("111","222","333","444","555","666","777","888","999"))
        val inspectionItemList = ArrayList<InspectionItem>()
        inspectionItemList.add(InspectionItem("外观","完成"))
        inspectionItemList.add(InspectionItem("底盘","待检"))
        inspectionItemList.add(InspectionItem("动态","待检"))
        inspectionItemList.add(InspectionItem("唯一性","待检"))
        inspectionItemList.add(InspectionItem("联网","待检"))
        vehicleInformationAdapter.data = vehicleInformationList
        inspectionItemAdapter.data = inspectionItemList
    }

}