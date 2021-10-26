package com.yxf.vehicleinspection.view.fragment

import android.util.Log
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleQueueBinding
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.view.adapter.VehicleQueueRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory
import java.util.*


class VehicleQueueFragment : BaseBindingFragment<FragmentVehicleQueueBinding>() {
    lateinit var adapter: VehicleQueueRvAdapter
    lateinit var viewModel: VehicleQueueViewModel
    lateinit var args: VehicleQueueFragmentArgs

    override fun init() {
        this.requireActivity().onBackPressedDispatcher.addCallback(this) {
            this@VehicleQueueFragment.findNavController().navigate(R.id.navHostFragment)
        }
        val args: VehicleQueueFragmentArgs by navArgs()
        this.args = args
        viewModel = ViewModelProvider(this, VehicleQueueViewModelFactory(
            VehicleQueueRepository()))
            .get(VehicleQueueViewModel::class.java)
        binding.rvVehicleQueue.layoutManager = LinearLayoutManager(this.requireContext())
        adapter = VehicleQueueRvAdapter(args.hostName)

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

    private fun getQueueData(hphm: String) {
        if (args.hostName == NavHostFragment.HOSTNAME_VERIFY_SIGNATURE) {
            viewModel.getVerifyDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, {

                adapter.data = it
            })
        } else {
            viewModel.getDataQueue(hphm.uppercase(Locale.getDefault())).observe(this, {

                adapter.data = it
            })
        }

    }

    override fun onResume() {
        super.onResume()
        getQueueData(binding.tvSercher.text.toString())
    }



}