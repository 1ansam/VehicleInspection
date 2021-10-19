package com.yxf.vehicleinspection.view.fragment


import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.FragmentVehicleImageVideoBinding
import com.yxf.vehicleinspection.repository.VehicleImageRepository
import com.yxf.vehicleinspection.repository.VehicleVideoRepository
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModelFactory


class VehicleImageVideoFragment : BaseBindingFragment<FragmentVehicleImageVideoBinding>() {
    private lateinit var adapterLeft : VehicleImageRvAdapter
    private lateinit var adapterRight : VehicleVideoRvAdapter
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
            requireActivity().onBackPressed()
        }
        binding.btnReject.setOnClickListener {
            it.findNavController().navigate(R.id.signatureFragment)
        }
    }
    private fun getImageData(Lsh : String, Jccs : String) {
        viewModel.getImageData(Lsh).observe(this, {
            adapterLeft.setModel(it)
        })
        viewModel.getVideoData(Lsh, Jccs).observe(this, {
//            adapterRight
        })
    }



}