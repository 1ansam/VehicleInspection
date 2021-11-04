package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentExteriorBinding
import com.yxf.vehicleinspection.repository.ImageItemRepository
import com.yxf.vehicleinspection.view.adapter.ImageItemRvAdapter
import com.yxf.vehicleinspection.viewModel.ExteriorViewModel
import com.yxf.vehicleinspection.viewModel.ExteriorViewModelFactory

class ExteriorFragment : BaseBindingFragment<FragmentExteriorBinding>() {
    lateinit var imageItemRvAdapter: ImageItemRvAdapter
    lateinit var exteriorViewModel: ExteriorViewModel
    val args : ExteriorFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        exteriorViewModel = ViewModelProvider(this,ExteriorViewModelFactory(ImageItemRepository())).get(ExteriorViewModel::class.java)
        binding.includeTitle.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_exteriorFragment_to_signatureFragment)
        }
        imageItemRvAdapter = ImageItemRvAdapter()
        binding.rvPhoto.layoutManager = LinearLayoutManager(this.requireContext())
        binding.rvPhoto.adapter = imageItemRvAdapter
    }
    fun getImageData(Lsh : String, Jyxm : String, Ajywlb : String, Hjywlb : String){
        exteriorViewModel.getImageItemData(Lsh, Jyxm, Ajywlb, Hjywlb)
    }
    override fun onResume() {
        getImageData(args.bean.Lsh,args.jyxm,
            args.bean.Ajywlb,args.bean.Hjywlb)
        super.onResume()
    }

}