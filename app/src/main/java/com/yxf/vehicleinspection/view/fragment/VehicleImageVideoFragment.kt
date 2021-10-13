package com.yxf.vehicleinspection.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.FragmentVehicleImageVideoBinding
import com.yxf.vehicleinspection.repository.VehicleImageRepository
import com.yxf.vehicleinspection.repository.VehicleQueueRepository
import com.yxf.vehicleinspection.repository.VehicleVideoRepository
import com.yxf.vehicleinspection.view.activity.SignatureActivity
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModelFactory
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModel
import com.yxf.vehicleinspection.viewModel.VehicleQueueViewModelFactory


class VehicleImageVideoFragment : BaseBindingFragment<FragmentVehicleImageVideoBinding>() {
    lateinit var adapterLeft : VehicleImageRvAdapter
    lateinit var adapterRight : VehicleVideoRvAdapter
    lateinit var viewModel : VehicleImageVideoViewModel

    override fun init() {
//        Debug.waitForDebugger();
//        this.requireActivity().onBackPressedDispatcher.addCallback(this){
//            this@VehicleImageVideoFragment.findNavController().navigate(R.id.vehicleQueueFragment)
//        }
        val bundle = arguments
        val model = bundle?.getSerializable("key") as VehicleQueueResponse
        val Lsh = model.Lsh
        val Jccs = "0"
        viewModel = ViewModelProvider(
            this,
            VehicleImageVideoViewModelFactory(
                VehicleImageRepository(),
                VehicleVideoRepository()
            )
        ).get(VehicleImageVideoViewModel::class.java)
        adapterLeft = VehicleImageRvAdapter(this.requireContext(),null)
        adapterRight = VehicleVideoRvAdapter(this.requireContext(), null)
        getImageData(Lsh,Jccs)
        binding.btnPass.setOnClickListener {
            it.findNavController().navigate(R.id.signatureFragment)

        }
        binding.btnExit.setOnClickListener {
            it.findNavController().navigate(R.id.vehicleQueueFragment)
        }
        binding.btnReject.setOnClickListener {
            it.findNavController().navigate(R.id.signatureFragment)
        }
    }
    fun getImageData(Lsh : String , Jccs : String) {
        viewModel.getImageData(Lsh).observe(this, Observer {
            adapterLeft.setModel(it)
        })
        viewModel.getVideoData(Lsh, Jccs).observe(this, Observer {
//            adapterRight
        })
    }



}