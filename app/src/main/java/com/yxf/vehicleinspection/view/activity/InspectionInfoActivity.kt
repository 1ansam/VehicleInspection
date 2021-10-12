package com.yxf.vehicleinspection.view.activity

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.ActivityInspectionInfoBinding
import com.yxf.vehicleinspection.repository.InspectionInfoRepository
import com.yxf.vehicleinspection.view.adapter.InspectionInfoRvAdapter
import com.yxf.vehicleinspection.viewModel.InspectionInfoViewModel
import com.yxf.vehicleinspection.viewModel.InspectionInfoViewModelFactory

class InspectionInfoActivity : BaseBindingActivity<ActivityInspectionInfoBinding>() {
    private val TAG = "InspectionInfoActivity"
    override fun init() {
        val bundle = intent.getBundleExtra("bundle")
        val data: VehicleQueueResponse = bundle?.getSerializable("key") as VehicleQueueResponse
        val viewModel = ViewModelProvider(this, InspectionInfoViewModelFactory(
            InspectionInfoRepository())).get(InspectionInfoViewModel::class.java)
        if (viewModel.inspectionInfoData.value != null) {
            val adapter = InspectionInfoRvAdapter(this,  viewModel.inspectionInfoData.value!!)
            binding.rvInspectionInfo.layoutManager = LinearLayoutManager(this)
            binding.rvInspectionInfo.adapter = adapter
        }
    }
}