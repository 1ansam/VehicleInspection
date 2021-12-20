package com.yxf.vehicleinspection.view.fragment

import android.view.View
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    val viewModel by viewModels<VehicleQueueViewModel> { VehicleQueueViewModelFactory((requireActivity().application as MyApp).vehicleQueueRepository) }
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun init() {
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VehicleQueueRvAdapter(this, sharedViewModel)
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)


        binding.svVehicleQueue.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getInspectionQueue(binding.svVehicleQueue.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun getInspectionQueue(hphm: String) {
        binding.pbVehicleQueue.visibility = View.VISIBLE
        when(sharedViewModel.hostName.value){
            NavHostFragment.HOSTNAME_CHARGE ->{
                viewModel.getChargeQueue(hphm.uppercase(Locale.getDefault())).observe(this) {
                    binding.pbVehicleQueue.visibility = View.GONE
                    adapter.data = it
                }
            }
            else -> {
                viewModel.getInspectionQueue(hphm.uppercase(Locale.getDefault())).observe(this) {
                    binding.pbVehicleQueue.visibility = View.GONE
                    adapter.data = it
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getInspectionQueue(binding.svVehicleQueue.query.toString())
    }


}