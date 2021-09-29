package com.yxf.vehicleinspection.view.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.bean.InspectionInfoBean
import com.yxf.vehicleinspection.bean.InspectionInfoItemBean
import com.yxf.vehicleinspection.databinding.ActivityInspectionInfoBinding
import com.yxf.vehicleinspection.repository.InspectionInfoRepository
import com.yxf.vehicleinspection.view.Adapter.InspectionInfoRvAdapter
import com.yxf.vehicleinspection.viewModel.InspectionInfoViewModel
import com.yxf.vehicleinspection.viewModel.InspectionInfoViewModelFactory

class InspectionInfoActivity : BaseBindingActivity<ActivityInspectionInfoBinding>() {
    private val TAG = "InspectionInfoActivity"
    override fun init() {
        val bundle = intent.getBundleExtra("bundle")
        val baseinfoHand: BaseInfo_Hand = bundle?.getSerializable("key") as BaseInfo_Hand
        val viewModel = ViewModelProvider(this, InspectionInfoViewModelFactory(
            InspectionInfoRepository())).get(InspectionInfoViewModel::class.java)
        if (viewModel.inspectionInfoData.value != null) {
            val adapter = InspectionInfoRvAdapter(this, viewModel.inspectionInfoData.value!!)
            binding.rvInspectionInfo.layoutManager = LinearLayoutManager(this)
            binding.rvInspectionInfo.adapter = adapter
        }
    }
}