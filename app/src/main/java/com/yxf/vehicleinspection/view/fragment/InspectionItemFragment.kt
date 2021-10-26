package com.yxf.vehicleinspection.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.InspectionItem
import com.yxf.vehicleinspection.bean.VehicleInformation
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.FragmentInspectionItemBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleInformationAdapter
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory

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
        vehicleInformationList.add(VehicleInformation("2021101915550001","222","333","444","555","666","777","888","999"))
        val inspectionItemList = ArrayList<InspectionItem>()
        inspectionItemList.add(InspectionItem("外观","待检"))
        inspectionItemList.add(InspectionItem("底盘","待检"))
        inspectionItemList.add(InspectionItem("动态","待检"))
        inspectionItemList.add(InspectionItem("唯一性","待检"))
        inspectionItemList.add(InspectionItem("联网","待检"))
        vehicleInformationAdapter.data = vehicleInformationList
        inspectionItemAdapter.data = inspectionItemList
    }


    override fun onResume() {
        super.onResume()
        getData()
        Log.e("TAG", "onResume: InspectionItemFragment", )
    }


}