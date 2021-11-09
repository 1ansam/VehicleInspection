package com.yxf.vehicleinspection.view.fragment


import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleImageVideoBinding
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModelFactory


class VehicleImageVideoFragment : BaseBindingFragment<FragmentVehicleImageVideoBinding>() {
    private lateinit var imageRvAdapter : VehicleImageRvAdapter
    private lateinit var videoRvAdapter : VehicleVideoRvAdapter
    private val vehicleImageVideoViewModel by
        viewModels<VehicleImageVideoViewModel> { VehicleImageVideoViewModelFactory(
            (requireActivity().application as MyApp).vehicleImageRepository,
            (requireActivity().application as MyApp).vehicleVideoRepository )}
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val Jccs = "0"

        imageRvAdapter = VehicleImageRvAdapter()
        videoRvAdapter = VehicleVideoRvAdapter()


        binding.btnPass.setOnClickListener {
            val action = VehicleImageVideoFragmentDirections.actionVehicleImageVideoFragmentToSignatureFragment()
            it.findNavController().navigate(action)

        }
        binding.btnExit.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnReject.setOnClickListener {
            val action = VehicleImageVideoFragmentDirections.actionVehicleImageVideoFragmentToSignatureFragment()
            it.findNavController().navigate(action)
        }
    }
    private fun getImageData(Lsh : String, Jccs : String) {
        vehicleImageVideoViewModel.getImageData(Lsh).observe(this, {
            imageRvAdapter.data = it
        })
        vehicleImageVideoViewModel.getVideoData(Lsh, Jccs).observe(this, {
            videoRvAdapter.data = it
        })
    }



}