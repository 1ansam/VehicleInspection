package com.yxf.vehicleinspection.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.bean.InspectionInfoBean
import com.yxf.vehicleinspection.databinding.InspectionInfoItemBinding
import com.yxf.vehicleinspection.viewModel.InspectionInfoViewModel

/**
 *   author:yxf
 *   time:2021/9/29
 */
class InspectionInfoRvAdapter(private val context : Context,  private val modelList : List<InspectionInfoBean>) : RecyclerView.Adapter<InspectionInfoViewHolder>(){
    lateinit var binding : InspectionInfoItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspectionInfoViewHolder {
        binding = InspectionInfoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return InspectionInfoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: InspectionInfoViewHolder, position: Int) {
        val model = modelList[position]
        val adapter = InspectionInfoItemRvAdapter(context, model.itemBeanList)
        holder.setTitle(model)
        holder.binding.rvVehicleInfo.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(adapter)
        }

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

}
class InspectionInfoViewHolder(val binding: InspectionInfoItemBinding): RecyclerView.ViewHolder(binding.root){

    fun setTitle(model : InspectionInfoBean){
        binding.tvInspectionInfoTitle.text = model.title
    }
}