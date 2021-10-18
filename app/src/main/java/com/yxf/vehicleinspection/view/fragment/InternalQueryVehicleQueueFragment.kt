package com.yxf.vehicleinspection.view.fragment

import android.opengl.Visibility
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.InternalQueryVehicleQueueAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvToVerifyAdapter
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class InternalQueryVehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    private val TAG = "VehicleQueueFragment"
    lateinit var internalQueryAdapter: InternalQueryVehicleQueueAdapter
    lateinit var viewModel : VehicleQueueViewModel
    override fun init() {


        viewModel = ViewModelProvider(this, VehicleQueueViewModelFactory(
            VehicleQueueRepository()))
            .get(VehicleQueueViewModel::class.java)
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())

        internalQueryAdapter = InternalQueryVehicleQueueAdapter()
            binding.rvVehicleQueue.adapter = internalQueryAdapter
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
        viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, {
            internalQueryAdapter.data = it
        })
    }
    override fun onResume() {
        super.onResume()
        getQueueData(binding.tvSercher.text.toString())
    }

}