package com.yxf.vehicleinspection.view.fragment


import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentVehicleImageVideoBinding
import com.yxf.vehicleinspection.repository.VehicleImageRepository
import com.yxf.vehicleinspection.repository.VehicleVideoRepository
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.SharedViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModel
import com.yxf.vehicleinspection.viewModel.VehicleImageVideoViewModelFactory


class VehicleImageVideoFragment : BaseBindingFragment<FragmentVehicleImageVideoBinding>() {
    private lateinit var imageRvAdapter : VehicleImageRvAdapter
    private lateinit var videoRvAdapter : VehicleVideoRvAdapter
    lateinit var viewModel : VehicleImageVideoViewModel
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        Debug.waitForDebugger();
        val Jccs = "0"
        viewModel = ViewModelProvider(
            this,
            VehicleImageVideoViewModelFactory(
                VehicleImageRepository(),
                VehicleVideoRepository()
            )
        ).get(VehicleImageVideoViewModel::class.java)
        imageRvAdapter = VehicleImageRvAdapter()
        videoRvAdapter = VehicleVideoRvAdapter()
        sharedViewModel.selectedBean.observe(this,{
            getImageData(it.Lsh,Jccs)
        })

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
        viewModel.getImageData(Lsh).observe(this, {
            imageRvAdapter.data = it
        })
        viewModel.getVideoData(Lsh, Jccs).observe(this, {
            videoRvAdapter.data = it
        })
    }



}