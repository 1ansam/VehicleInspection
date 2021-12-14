package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.AdministrativeR023Response
import com.yxf.vehicleinspection.databinding.ItemAdministrativeBinding

/**
 *   author:yxf
 *   time:2021/12/14
 */
class AdministrativeAdapter : BaseRvAdapter<AdministrativeR023Response,ItemAdministrativeBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemAdministrativeBinding> {
        return BaseRvViewHolder(ItemAdministrativeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemAdministrativeBinding>,
        position: Int,
        binding: ItemAdministrativeBinding,
        bean: AdministrativeR023Response
    ) {
        holder.binding.apply {
            tvXzqhdm.text = bean.Xzqhdm
            tvXzqhmc.text = bean.Xzqhmc
        }
        holder.itemView.setOnClickListener {
            onItemViewClickListener?.onItemClick(holder.itemView,position,bean)
        }
    }
}