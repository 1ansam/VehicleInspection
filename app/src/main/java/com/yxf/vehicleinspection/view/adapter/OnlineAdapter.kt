package com.yxf.vehicleinspection.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.Jcgw
import com.yxf.vehicleinspection.bean.response.OnlineStatusR024Response
import com.yxf.vehicleinspection.databinding.ItemOnlineBinding

/**
 *   author:yxf
 *   time:2021/12/20
 */
class OnlineAdapter : BaseRvAdapter<Jcgw,ItemOnlineBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemOnlineBinding> {
        return BaseRvViewHolder(ItemOnlineBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemOnlineBinding>,
        position: Int,
        binding: ItemOnlineBinding,
        bean: Jcgw
    ) {
        holder.binding.apply {
            tvGwmc.text = bean.Gwmc
            tvGwzt.text = bean.Gwzt
        }

    }



}