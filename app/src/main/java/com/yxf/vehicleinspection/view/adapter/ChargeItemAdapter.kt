package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ChargeItemR004Response
import com.yxf.vehicleinspection.databinding.ItemChargeBinding

/**
 *   author:yxf
 *   time:2021/12/22
 *   收费条目RecyclerView Adapter
 */
class ChargeItemAdapter : BaseRvAdapter<ChargeItemR004Response, ItemChargeBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemChargeBinding> {
        return BaseRvViewHolder(ItemChargeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemChargeBinding>,
        position: Int,
        binding: ItemChargeBinding,
        bean: ChargeItemR004Response
    ) {
        binding.tvSpbh.text = bean.Spbh
        binding.cbSpmc.text = bean.Spmc
        binding.tvSpdj.text = bean.Spdj
    }
}