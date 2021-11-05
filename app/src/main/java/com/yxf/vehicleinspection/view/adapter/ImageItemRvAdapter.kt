package com.yxf.vehicleinspection.view.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yxf.vehicleinspection.R
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.response.ImageItemResponse
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding

/**
 *   author:yxf
 *   time:2021/11/4
 */
class ImageItemRvAdapter() : BaseRvAdapter<ImageItemResponse, VehicleImageItemBinding>() {
    var bitmap : Bitmap? = null
    set(value) {
        field  = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRvViewHolder<VehicleImageItemBinding> {
        return BaseRvViewHolder(VehicleImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: BaseRvViewHolder<VehicleImageItemBinding>,
        position: Int,
        binding: VehicleImageItemBinding,
        bean: ImageItemResponse,
    ) {
        holder.apply {
            binding.tvZpmc.text = bean.Zpmc
        }
        holder.binding.ivImage.setOnClickListener {
            onItemViewClickListener?.onItemClick(holder.itemView,
                position)
        }


    }
}