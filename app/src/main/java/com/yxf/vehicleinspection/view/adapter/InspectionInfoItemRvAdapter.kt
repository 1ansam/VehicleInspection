package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.InspectionInfoItemBean
import com.yxf.vehicleinspection.databinding.VehicleInfoItemBinding

/**
 *   author:yxf
 *   time:2021/9/29
 */
class InspectionInfoItemRvAdapter(private val context: Context,private val modelList: ArrayList<InspectionInfoItemBean>) : RecyclerView.Adapter<InspectionInfoItemViewHolder>(){
    lateinit var binding : VehicleInfoItemBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InspectionInfoItemViewHolder {
        binding = VehicleInfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InspectionInfoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InspectionInfoItemViewHolder, position: Int) {
        holder.setData(modelList[position])
    }

    override fun getItemCount(): Int {
        return modelList.size
    }
}
class InspectionInfoItemViewHolder(private val binding : VehicleInfoItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setData(model : InspectionInfoItemBean){
        binding.tvItemName.text = model.itemName
        binding.tvItemValue.text = model.itemValue
    }
}