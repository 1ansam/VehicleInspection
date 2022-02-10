package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.bean.response.UserInfoR001Response
import com.yxf.vehicleinspection.bean.response.VehicleQueueR002Response
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.view.activity.DisplayActivity
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    lateinit var bean001 : UserInfoR001Response
    val viewModel by viewModels<VehicleQueueViewModel> { VehicleQueueViewModelFactory((requireActivity().application as MyApp).vehicleQueueRepository) }
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        bean001 = DisplayActivity.bean001 as UserInfoR001Response
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VehicleQueueRvAdapter(this, sharedViewModel)
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)
        binding.svVehicleQueue.setOnClickListener {
            binding.svVehicleQueue.onActionViewExpanded()
        }
        binding.svVehicleQueue.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getInspectionQueue(query?:"")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        binding.refresh.setOnRefreshListener {
            getInspectionQueue(binding.svVehicleQueue.query.toString())
            binding.refresh.isRefreshing = false
        }

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