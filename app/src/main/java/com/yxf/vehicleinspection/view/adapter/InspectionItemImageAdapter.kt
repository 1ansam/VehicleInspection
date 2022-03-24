package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ImageItemR017Response
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding

/**
 * @author : yxf
 * @Date : 2021/11/7
 **/
class InspectionItemImageAdapter : BaseRvAdapter<ImageItemR017Response, VehicleImageItemBinding>() {

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
        binding.apply {
            tvZpmc.text = bean.Zpmc
            tvZpajdm.text = root.resources.getString(R.string.zpajdm_,bean.Zpajdm)
            tvZphjdm.text = root.resources.getString(R.string.zphjdm_,bean.Zphjdm)
        }
        binding.ivImage.setOnClickListener {
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
