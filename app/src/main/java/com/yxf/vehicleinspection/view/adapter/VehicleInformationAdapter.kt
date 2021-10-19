package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.VehicleInformation
import com.yxf.vehicleinspection.databinding.RvItemVehicleInformationBinding

/**
 *   author:yxf
 *   time:2021/10/19
 */
class VehicleInformationAdapter : BaseRvAdapter<VehicleInformation,RvItemVehicleInformationBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<RvItemVehicleInformationBinding> {
        return BaseRvViewHolder(RvItemVehicleInformationBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<RvItemVehicleInformationBinding>,
        position: Int,
        binding: RvItemVehicleInformationBinding,
        bean: VehicleInformation,
    ) {
        holder.apply {
            binding.tvLsh.text = bean.Lsh
            binding.tvHphm.text = bean.Lsh
            binding.tvHpzl.text = bean.Lsh
            binding.tvHpys.text = bean.Lsh
            binding.tvCllx.text = bean.Lsh
            binding.tvWkcc.text = bean.Lsh
            binding.tvZbzl.text = bean.Lsh
            binding.tvLtgg.text = bean.Lsh
            binding.tvCcrq.text = bean.Lsh
        }
    }
}