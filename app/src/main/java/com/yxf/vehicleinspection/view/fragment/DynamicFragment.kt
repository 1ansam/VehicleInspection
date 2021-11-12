package com.yxf.vehicleinspection.view.fragment

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseBindingFragment
import com.yxf.vehicleinspection.databinding.FragmentDynamicBinding
import com.yxf.vehicleinspection.view.adapter.InspectionItemSelectAdapter
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModel
import com.yxf.vehicleinspection.viewModel.InspectionItemViewModelFactory

class DynamicFragment : BaseBindingFragment<FragmentDynamicBinding>() {
    private val inspectionItemViewModel by viewModels<InspectionItemViewModel> { InspectionItemViewModelFactory((requireActivity().application as MyApp).inspectionItemRepository,(requireActivity().application as MyApp).serverTimeRepository) }
    lateinit var inspectionItemSelectAdapter: InspectionItemSelectAdapter
    private val args: DynamicFragmentArgs by navArgs()
    override fun init() {
        this.requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.includeTitle.btnSubmit.setOnClickListener {
            val action = DynamicFragmentDirections.actionDynamicFragmentToSignatureFragment(args.bean006,args.bean005,args.jcxh)
            findNavController().navigate(action)
        }
        binding.rvSelect.layoutManager = LinearLayoutManager(this.requireContext())
        inspectionItemSelectAdapter = InspectionItemSelectAdapter()
        binding.rvSelect.adapter = inspectionItemSelectAdapter
    }
    private fun getSelectData(Lsh: String, Jyxm: String, Ajywlb: String, Hjywlb: String){
        inspectionItemViewModel.getSelectItemData(Lsh, Jyxm, Ajywlb, Hjywlb).observe(this){
            val artificialProjectR016Response = it
            inspectionItemSelectAdapter.data = artificialProjectR016Response.Xmlb
        }
    }

    override fun onResume() {
        getSelectData(args.bean006.Lsh, args.bean006.Jcxm,
            args.bean006.Ajywlb, args.bean006.Hjywlb)
        super.onResume()
    }
}