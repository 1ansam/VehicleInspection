package com.yxf.vehicleinspection.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleQueueResponse
import com.yxf.vehicleinspection.databinding.VehicleQueueItemBinding

/**
 *   author:yxf
 *   time:2021/10/18
 */
class ChassisVehicleQueueAdapter : BaseRvAdapter<VehicleQueueResponse, VehicleQueueItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleQueueItemBinding> {
        return BaseRvViewHolder(VehicleQueueItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleQueueItemBinding>,
        position: Int,
        binding: VehicleQueueItemBinding,
        bean: VehicleQueueResponse,
    ) {
        holder.apply {
            binding.tvHphm.text = bean.Hphm
            binding.tvHpzl.text = bean.HpzlCc
            binding.tvAjywlb.text = "安检：${bean.AjywlbCc}"
            binding.tvHjjwlb.text = "环保：${bean.HjywlbCc}"
            binding.tvLsh.text = bean.Lsh
            binding.tvTime.text = bean.Djrq?.substring(0,16)
            binding.tvInspectionState.text = bean.Jyzt
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("key", bean)
            it.findNavController().navigate(R.id.chassisItemFragment,bundle)
        }
    }
}