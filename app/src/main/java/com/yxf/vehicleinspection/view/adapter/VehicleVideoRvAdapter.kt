package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yxf.vehicleinspection.MyApp.Companion.context
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.base.BaseUrlHelper
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
        holder.binding.tvZpmc.text = bean.Spmc
        holder.binding.vvVideo.setOnClickListener {
            VideoPickerFragment(bean).show(fragment.childFragmentManager,bean.Xmbh)
        }
//        holder.binding.vvVideo.apply {
//            setVideoPath("${BaseUrlHelper.instance.httpUrl}${bean.Lxdz}")
//            seekTo(1)
//        }
    }
}