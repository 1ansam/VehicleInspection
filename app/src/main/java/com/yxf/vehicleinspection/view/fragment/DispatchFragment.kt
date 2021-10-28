package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentDispatchBinding
import com.yxf.vehicleinspection.viewModel.SharedViewModel

class DispatchFragment : BaseBindingFragment<FragmentDispatchBinding>() {
    private val sharedViewModel : SharedViewModel by activityViewModels()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        sharedViewModel.selectedBean.observe(this,{
            binding.tvLsh.text = it.Lsh
        })
        binding.btnDynamicInspection.setOnClickListener {
            val action = DispatchFragmentDirections.actionDispatchFragmentToDynamicFragment()
            findNavController().navigate(action)
        }
    }

}