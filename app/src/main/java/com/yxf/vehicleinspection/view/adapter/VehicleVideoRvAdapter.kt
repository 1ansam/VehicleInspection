package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response
import com.yxf.vehicleinspection.databinding.VehicleVideoItemBinding

/**
 *   author:yxf
 *   time:2021/10/28
 */
class VehicleVideoRvAdapter : BaseRvAdapter<VehicleVideoR008Response, VehicleVideoItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleVideoItemBinding> {
        val binding = VehicleVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleVideoItemBinding>,
        position: Int,
        binding: VehicleVideoItemBinding,
        bean: VehicleVideoR008Response,
    ) {
        holder.binding.tvZpmc.text = bean.Spmc
    }
}