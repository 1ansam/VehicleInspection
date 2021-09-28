package com.yxf.vehicleinspection.view.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding

/**
 *   author:yxf
 *   time:2021/9/28
 */
class PersonInspcetionAdapter(protected val vehicleList : ArrayList<BaseInfo_Hand>) : RecyclerView.Adapter<PersonInspectionViewHolder>() {
    lateinit var binding: PersonInspectionItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInspectionViewHolder {
        binding = PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PersonInspectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonInspectionViewHolder, position: Int) {
        val vehicle : BaseInfo_Hand = vehicleList[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }


}
class PersonInspectionViewHolder(private val binding: PersonInspectionItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(bean : BaseInfo_Hand){
        binding.tvHphm.text = bean.hphm
    }
}