package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.VehicleFeature
import com.yxf.vehicleinspection.databinding.ItemVehicleFeatureBinding

/**
 *   author:yxf
 *   time:2021/11/15
 */
class InspectionItemVehicleFeatureAdapter :
    BaseRvAdapter<VehicleFeature, ItemVehicleFeatureBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<ItemVehicleFeatureBinding> {
        val binding = ItemVehicleFeatureBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<ItemVehicleFeatureBinding>,
        position: Int,
        binding: ItemVehicleFeatureBinding,
        bean: VehicleFeature,
    ) {
        holder.binding.tvFeatureName.text = bean.featureName
    }
}