package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfo005Response
import com.yxf.vehicleinspection.bean.response.VehicleInspectionItemResponse
import com.yxf.vehicleinspection.databinding.RvItemInspectionItemBinding
import com.yxf.vehicleinspection.view.fragment.InspectionItemFragmentDirections

/**
 *   author:yxf
 *   time:2021/10/19
 */
class InspectionItemAdapter() : BaseRvAdapter<VehicleInspectionItemResponse,RvItemInspectionItemBinding>() {
    var bean005 : VehicleAllInfo005Response? = null
        set(value) {
        field = value
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<RvItemInspectionItemBinding> {
        return BaseRvViewHolder(RvItemInspectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<RvItemInspectionItemBinding>,
        position: Int,
        binding: RvItemInspectionItemBinding,
        bean: VehicleInspectionItemResponse,
    ) {
        holder.apply {
            binding.tvJyxm.text = bean.Xmmc
            binding.tvJyzt.text = bean.Jczt
        }
//        if (binding.tvJyzt.text.equals("完成"))
//            holder.itemView.isEnabled = false
        holder.itemView.setOnClickListener {
            when(bean.Xmbh){
                "F1" -> it.findNavController().navigate(InspectionItemFragmentDirections.actionInspectionItemFragmentToExteriorFragment(bean,bean005!!))
                "C1" -> it.findNavController().navigate(R.id.chassisFragment)
                "DC" -> it.findNavController().navigate(R.id.dynamicFragment)
                "NQ" -> it.findNavController().navigate(R.id.networkQueryFragment)
                "UC" -> it.findNavController().navigate(R.id.uniqueFragment)
            }
        }

    }


}