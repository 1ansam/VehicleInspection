package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleAllInfoR005Response
import com.yxf.vehicleinspection.databinding.ItemRegisterBinding

/**
 *   author:yxf
 *   time:2021/12/3
 */
class RegisterListAdapter() : BaseRvAdapter<String,ItemRegisterBinding>() {
    var value : ArrayList<String?> = ArrayList<String?>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemRegisterBinding> {
        return BaseRvViewHolder(ItemRegisterBinding.inflate(LayoutInflater.from(parent.context,),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemRegisterBinding>,
        position: Int,
        binding: ItemRegisterBinding,
        bean: String,
    ) {
        binding.key.text = bean
        if (!value.isNullOrEmpty()){
            binding.value.setText(value[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}