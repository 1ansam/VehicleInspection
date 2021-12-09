package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
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
    val viewModel by viewModels<VehicleQueueViewModel> { VehicleQueueViewModelFactory((requireActivity().application as MyApp).vehicleQueueRepository) }
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VehicleQueueRvAdapter(this, sharedViewModel)
        binding.rvVehicleQueue.adapter = adapter
        binding.rvVehicleQueue.setHasFixedSize(true)

//        binding.btnSercher.setOnClickListener {
////            修改使用BaseUrlHelper反射方法
////            BaseUrlHelper.instance.setUrlField("http://192.168.1.1:8080")
//            getQueueData(binding.tvSearcher.text.toString())
//        }
        binding.svVehicleQueue.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                getQueueData(binding.svVehicleQueue.query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
//        在Edittext文字改变后自动获取数据
//        binding.tvSearcher.doAfterTextChanged {
//            getData(binding.tvSearcher.text.toString())
//        }
    }

    private fun getQueueData(hphm: String) {
        binding.pbVehicleQueue.visibility = View.VISIBLE
        sharedViewModel.hostName.observe(this) { hostName ->
            if (hostName == NavHostFragment.HOSTNAME_VERIFY_SIGNATURE) {
                viewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault())).observe(this) {
                    binding.pbVehicleQueue.visibility = View.GONE
                    adapter.data = it
                }
            } else {
                binding.pbVehicleQueue.visibility = View.GONE
                viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this) {
                    adapter.data = it
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        getQueueData(binding.svVehicleQueue.query.toString())
    }


}