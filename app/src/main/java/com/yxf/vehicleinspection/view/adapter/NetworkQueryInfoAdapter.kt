package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.NetworkQueryInfo
import com.yxf.vehicleinspection.databinding.ItemNetworkQueryInfoBinding

/**
 *   author:yxf
 *   time:2021/11/25
 */
class NetworkQueryInfoAdapter : BaseRvAdapter<NetworkQueryInfo,ItemNetworkQueryInfoBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemNetworkQueryInfoBinding> {
        return BaseRvViewHolder(ItemNetworkQueryInfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemNetworkQueryInfoBinding>,
        position: Int,
        binding: ItemNetworkQueryInfoBinding,
        bean: NetworkQueryInfo,
    ) {
        binding.tvNetworkQueryItemName.text = bean.name
        binding.tvNetworkQueryItemValue.setText(bean.value)
    }
}