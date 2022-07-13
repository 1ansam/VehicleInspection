package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseBean
import com.yxf.vehicleinspection.base.BaseRvMapAdapter
import com.yxf.vehicleinspection.base.BaseRvMapViewHolder
import com.yxf.vehicleinspection.databinding.ItemRegisterBinding

/**
 *   author:yxf
 *   time:2022/7/1
 */
class RegisterListMapAdapter : BaseRvMapAdapter<String, List<String>, ItemRegisterBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvMapViewHolder<ItemRegisterBinding> {
        return BaseRvMapViewHolder(ItemRegisterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvMapViewHolder<ItemRegisterBinding>,
        position: Int,
        binding: ItemRegisterBinding,
        bean: BaseBean<String, List<String>>
    ) {
        binding.key.text = bean.key
        binding.value.setText(bean.value?.get(1)?:"")
    }
}