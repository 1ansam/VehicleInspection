package com.yxf.vehicleinspection.view.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingActivity
import com.yxf.vehicleinspection.databinding.ActivityPersonInspectionBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*

class PersonInspectionActivity : BaseBindingActivity<ActivityPersonInspectionBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    lateinit var viewModel : VehicleQueueViewModel
    override fun init() {
        viewModel = ViewModelProvider(this, VehicleQueueViewModelFactory(
            VehicleQueueRepository()))
            .get(VehicleQueueViewModel::class.java)
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this)
        adapter = VehicleQueueRvAdapter(this,null)
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)
        getQueueData("")
        binding.btnSercher.setOnClickListener {
//            修改使用BaseUrlHelper类反射方法
//            BaseUrlHelper.instance.setHostField("192.168.31.70")
            getQueueData(binding.tvSercher.text.toString())
        }
//        在Edittext文字改变后自动获取数据
//        binding.tvSercher.doAfterTextChanged {
//            getData(binding.tvSercher.text.toString())
//        }
    }
    private fun getQueueData(hphm : String){
        viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, Observer {
            adapter.setModel(it)
        })
    }
    override fun onResume() {
        super.onResume()
        getQueueData(binding.tvSercher.text.toString())
    }
}
