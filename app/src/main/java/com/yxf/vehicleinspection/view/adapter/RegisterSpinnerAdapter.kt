package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.databinding.ItemRegisterSpinnerBinding

/**
 *   author:yxf
 *   time:2021/12/3
 */
class RegisterSpinnerAdapter(val context : Context) : BaseRvAdapter<String, ItemRegisterSpinnerBinding>() {
    var spinnerData : List<List<String>> = ArrayList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemRegisterSpinnerBinding> {
        return BaseRvViewHolder(ItemRegisterSpinnerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemRegisterSpinnerBinding>,
        position: Int,
        binding: ItemRegisterSpinnerBinding,
        bean: String,
    ) {
        binding.tvSpinnerKey.text = bean
        if (spinnerData.isNotEmpty()){
            binding.tvSpinnerValue.adapter = ArrayAdapter(context, R.layout.textview_spinner,spinnerData[position])
        }

    }
}