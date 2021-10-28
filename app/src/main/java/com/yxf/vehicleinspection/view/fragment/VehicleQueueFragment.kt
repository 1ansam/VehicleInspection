package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    lateinit var viewModel: VehicleQueueViewModel
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        viewModel = ViewModelProvider(this, VehicleQueueViewModelFactory(
            VehicleQueueRepository()))
            .get(VehicleQueueViewModel::class.java)
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VehicleQueueRvAdapter(this,sharedViewModel)
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)
//        getQueueData("")

        binding.btnSercher.setOnClickListener {
//            修改使用BaseUrlHelper反射方法
//            BaseUrlHelper.instance.setUrlField("http://192.168.1.1:8080")
            getQueueData(binding.tvSercher.text.toString())


        }
//        在Edittext文字改变后自动获取数据
//        binding.tvSercher.doAfterTextChanged {
//            getData(binding.tvSercher.text.toString())
//        }
    }

    private fun getQueueData(hphm: String) {
        sharedViewModel.hostName.observe(this,{ hostName ->
            if (hostName == NavHostFragment.HOSTNAME_VERIFY_SIGNATURE) {
                viewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, {
                    adapter.data = it
                })
            } else{
                viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, {
                    adapter.data = it
                })
            }
        })


    }

    override fun onResume() {
        super.onResume()
        getQueueData(binding.tvSercher.text.toString())
    }



}