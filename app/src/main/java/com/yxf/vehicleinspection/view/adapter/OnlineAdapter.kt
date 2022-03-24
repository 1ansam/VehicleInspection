package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.Jcgw
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
        binding.tvGwmc.text = bean.Gwmc
        when(bean.Gwzt){
            "0" ->{
                binding.tvGwzt.apply {
                    text = "未开始"
                    setTextColor(resources.getColor(R.color.red,null))
                }
            }
            "-" ->{
                binding.tvGwzt.apply {
                    text = "未开始"
                    setTextColor(resources.getColor(R.color.red,null))
                }
            }
            "1" ->{
                binding.tvGwzt.apply {
                    text = "已开始"
                    setTextColor(resources.getColor(R.color.green,null))
                }
            }
            "2" ->{
                binding.tvGwzt.apply {
                    text = "成功"
                    setTextColor(resources.getColor(R.color.blue,null))
                }
            }
            "3" ->{
                binding.tvGwzt.apply {
                    text = "结束"
                    setTextColor(resources.getColor(R.color.blue,null))
                }
            }
        }

    }



}