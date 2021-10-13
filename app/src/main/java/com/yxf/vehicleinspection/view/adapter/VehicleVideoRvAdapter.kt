package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.response.VehicleVideoResponse
import com.yxf.vehicleinspection.databinding.VehicleVideoItemBinding

/**
 *   author:yxf
 *   time:2021/10/12
 */
class VehicleVideoRvAdapter(val context: Context, val modelList: List<VehicleVideoResponse>?) : RecyclerView.Adapter<VehicleVideoViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleVideoViewHolder {
        val binding = VehicleVideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VehicleVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleVideoViewHolder, position: Int) {
        if (modelList!= null){
            holder.setModel(modelList[position])
        }

    }

    override fun getItemCount(): Int {
        return modelList?.size ?: 0
    }
}
class VehicleVideoViewHolder(binding : VehicleVideoItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setModel(model : VehicleVideoResponse){

    }
}