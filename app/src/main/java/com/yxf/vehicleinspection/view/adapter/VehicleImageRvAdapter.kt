package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleImageR007Response
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding
import com.yxf.vehicleinspection.utils.base642Bitmap

/**
 *   author:yxf
 *   time:2021/10/28
 */
class VehicleImageRvAdapter : BaseRvAdapter<VehicleImageR007Response,VehicleImageItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleImageItemBinding> {
        val binding = VehicleImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleImageItemBinding>,
        position: Int,
        binding: VehicleImageItemBinding,
        bean: VehicleImageR007Response,
    ) {
        holder.binding.ivImage.setImageBitmap(base642Bitmap(bean.Zp))
        holder.binding.tvZpmc.text = bean.ZpzlMc
    }
}