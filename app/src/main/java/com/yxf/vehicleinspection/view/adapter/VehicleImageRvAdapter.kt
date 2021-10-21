package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.response.VehicleImageResponse
import com.yxf.vehicleinspection.databinding.VehicleImageItemBinding
import com.yxf.vehicleinspection.utils.ImageUtil

/**
 *   author:yxf
 *   time:2021/10/12
 */
class VehicleImageRvAdapter(val context : Context, var modelList: List<VehicleImageResponse>?) : RecyclerView.Adapter<VehicleImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleImageViewHolder {
        val binding = VehicleImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VehicleImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleImageViewHolder, position: Int) {
        if(modelList != null){
            val model = modelList!![position]
            holder.setData(model)
        }
    }

    override fun getItemCount(): Int {
        return modelList?.size ?: 0
    }
    fun setModel(modelList : List<VehicleImageResponse>){
        this.modelList = modelList
        notifyDataSetChanged()
    }
}
class VehicleImageViewHolder(val binding : VehicleImageItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setData(model : VehicleImageResponse){
        binding.tvItemTitle.text = model.ImageName
        binding.ivImage.setImageBitmap(ImageUtil.base642Bitmap(model.Zp))
    }
}