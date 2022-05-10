package com.yxf.vehicleinspection.view.fragment


import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.bean.response.VehicleImageR007Response
import com.yxf.vehicleinspection.databinding.FragmentVehicleImageVideoBinding
import com.yxf.vehicleinspection.utils.base642Bitmap
import com.yxf.vehicleinspection.view.adapter.VehicleImageRvAdapter
import com.yxf.vehicleinspection.view.adapter.VehicleVideoRvAdapter
import com.yxf.vehicleinspection.viewModel.*


class VehicleImageVideoFragment : BaseBindingFragment<FragmentVehicleImageVideoBinding>() {
    private var imageRvAdapter = VehicleImageRvAdapter()
    private lateinit var videoRvAdapter : VehicleVideoRvAdapter

    private val verifyViewModel by
        viewModels<VerifyViewModel> { VerifyViewModelFactory(
            (requireActivity().application as MyApp).verifyRepository)}
    private val args : VehicleImageVideoFragmentArgs by navArgs()

    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        videoRvAdapter = VehicleVideoRvAdapter(this)
        binding.rvLeft.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvLeft.adapter = imageRvAdapter
        binding.rvRight.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvRight.adapter = videoRvAdapter
        getImageData(args.bean013.Ajlsh, args.bean013.Hjlsh)

        binding.btnPass.setOnClickListener {
            val action = VehicleImageVideoFragmentDirections.actionVehicleImageVideoFragmentToSignatureVerifyFragment(args.bean013,1)
            it.findNavController().navigate(action)

        }
        binding.btnExit.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnReject.setOnClickListener {
            val action = VehicleImageVideoFragmentDirections.actionVehicleImageVideoFragmentToSignatureVerifyFragment(args.bean013,0)
            it.findNavController().navigate(action)
        }
    }
    private fun getImageData(ajLsh: String, hjLsh: String) {
        verifyViewModel.getVehicleImage(ajLsh,hjLsh).observe(this) {
            val list = ArrayList<VehicleImageR007Response>()
            for (element in it){
                list.add(VehicleImageR007Response(element.ID,element.Lsh,element.Jcxdh,element.Jycs,element.Hphm,element.Hpzl,element.Zp,element.Pssj,element.Jyxm,element.Zpzl,element.Ywlb,element.ImageName,element.ZpzlMc,
                    base642Bitmap(element.Zp)))
            }
            imageRvAdapter.data = list
        }
        verifyViewModel.getVehicleVideo(ajLsh, hjLsh).observe(this) {
            videoRvAdapter.data = it
        }
    }



}