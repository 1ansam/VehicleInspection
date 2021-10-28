package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.InspectionItem
import com.yxf.vehicleinspection.bean.VehicleInformation
import com.yxf.vehicleinspection.databinding.FragmentInspectionItemBinding
import com.yxf.vehicleinspection.view.adapter.InspectionItemAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleInformationAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel

class InspectionItemFragment : BaseBindingFragment<FragmentInspectionItemBinding>() {
    private lateinit var vehicleInformationAdapter : VehicleInformationAdapter
    private lateinit var inspectionItemAdapter : InspectionItemAdapter
    private val sharedViewModel : SharedViewModel by activityViewModels()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.rvVehicleInformation.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvInspectionItem.layoutManager = LinearLayoutManager(this.requireContext())
        vehicleInformationAdapter = VehicleInformationAdapter()
        inspectionItemAdapter = InspectionItemAdapter()
        binding.rvVehicleInformation.adapter = vehicleInformationAdapter
        binding.rvInspectionItem.adapter = inspectionItemAdapter
        binding.rvVehicleInformation.setHasFixedSize(true)
        binding.rvInspectionItem.setHasFixedSize(true)
        getData()
    }

    private fun getData() {

        sharedViewModel.selectedBean.observe(this,{
            val vehicleInformationList = ArrayList<VehicleInformation>()
            val inspectionItemList = ArrayList<InspectionItem>()
            vehicleInformationList.add(VehicleInformation(it.Lsh,it.Hphm,it.HpzlCc,it.HpysCc,"555","666","777","888","999"))
            inspectionItemList.add(InspectionItem("外观","待检"))
            inspectionItemList.add(InspectionItem("底盘","待检"))
            inspectionItemList.add(InspectionItem("动态","待检"))
            inspectionItemList.add(InspectionItem("唯一性","待检"))
            inspectionItemList.add(InspectionItem("联网","待检"))
            vehicleInformationAdapter.data = vehicleInformationList
            inspectionItemAdapter.data = inspectionItemList

        })

    }


    override fun onResume() {
        super.onResume()
//        getData()
        Log.e("TAG", "onResume: InspectionItemFragment")
    }


}