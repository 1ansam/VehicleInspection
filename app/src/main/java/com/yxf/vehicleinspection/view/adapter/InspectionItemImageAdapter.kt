package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding

/**
 * @author : yxf
 * @Date : 2021/11/7
 **/
class InspectionItemImageAdapter() : BaseRvAdapter<ImageItemR017Response, VehicleImageItemBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleImageItemBinding> {
        return BaseRvViewHolder(
            VehicleImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleImageItemBinding>,
        position: Int,
        binding: VehicleImageItemBinding,
        bean: ImageItemR017Response,
    ) {
        holder.apply {
            binding.tvZpmc.text = bean.Zpmc
            binding.tvZpajdm.text = "安检代码：${bean.Zpajdm}"
            binding.tvZphjdm.text = "环检代码：${bean.Zphjdm}"
        }
        holder.binding.ivImage.setOnClickListener {
            onItemViewClickListener?.onItemClick(
                holder.itemView,
                position,
                bean
            )
        }


    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
