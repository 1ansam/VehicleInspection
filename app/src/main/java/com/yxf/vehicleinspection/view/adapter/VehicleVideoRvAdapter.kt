package com.yxf.vehicleinspection.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.VehicleVideoR008Response
import com.yxf.vehicleinspection.databinding.VehicleVideoItemBinding
import com.yxf.vehicleinspection.view.VideoPickerFragment
import com.yxf.vehicleinspection.view.fragment.VehicleImageVideoFragment

/**
 *   author:yxf
 *   time:2021/10/28
 */
class VehicleVideoRvAdapter(val fragment: VehicleImageVideoFragment) : BaseRvAdapter<VehicleVideoR008Response, VehicleVideoItemBinding>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleVideoItemBinding> {
        val binding = VehicleVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BaseRvViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleVideoItemBinding>,
        position: Int,
        binding: VehicleVideoItemBinding,
        bean: VehicleVideoR008Response,
    ) {
        binding.tvZpmc.text = bean.Spmc
        binding.vvVideo.setOnClickListener {
            VideoPickerFragment(bean).show(fragment.childFragmentManager,bean.Xmbh)
        }

    }
}