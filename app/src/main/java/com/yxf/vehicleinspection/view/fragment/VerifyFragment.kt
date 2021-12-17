package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class VerifyFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    val viewModel by viewModels<VehicleQueueViewModel> { VehicleQueueViewModelFactory((requireActivity().application as MyApp).vehicleQueueRepository) }
    override fun init() {
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)
        getQueueData("")
    }
    private fun getQueueData(hphm : String){
        viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this) {
            adapter.data = it
        }
    }
    override fun onResume() {
        super.onResume()
        getQueueData(binding.svVehicleQueue.query.toString())
    }

}