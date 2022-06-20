package com.yxf.vehicleinspection.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.databinding.ItemRvJyxmBinding

/**
 *   author:yxf
 *   time:2021/12/6
 *   注册登记需要检验的项目Adapter
 */
class RegisterJyxmAdapter : BaseRvAdapter<String,ItemRvJyxmBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRvViewHolder<ItemRvJyxmBinding> {
        return BaseRvViewHolder(ItemRvJyxmBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemRvJyxmBinding>,
        position: Int,
        binding: ItemRvJyxmBinding,
        bean: String
    ) {
        if (bean == "联网查询"||bean == "唯一性检查"||bean == "外观"){
            binding.cbJyxm.isChecked = true
        }
        binding.cbJyxm.text = bean

    }
}