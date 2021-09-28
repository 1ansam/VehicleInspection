package com.yxf.vehicleinspection.view.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.yxf.vehicleinspection.MyApp
import com.yxf.vehicleinspection.base.BaseRvAdapter
import com.yxf.vehicleinspection.base.BaseRvViewHolder
import com.yxf.vehicleinspection.bean.BaseInfo_Hand
import com.yxf.vehicleinspection.databinding.PersonInspectionItemBinding
import com.yxf.vehicleinspection.view.Activity.InspectionInfoActivity

/**
 *   author:yxf
 *   time:2021/9/28
 */
class PersonInspcetionAdapter(private val context: Context, protected val vehicleList : List<BaseInfo_Hand>) : RecyclerView.Adapter<PersonInspectionViewHolder>() {
    lateinit var binding: PersonInspectionItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonInspectionViewHolder {
        binding = PersonInspectionItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PersonInspectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonInspectionViewHolder, position: Int) {
        for (index in 0 until vehicleList.size){
            val vehicle : BaseInfo_Hand = vehicleList[index]
            holder.setData(vehicle)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context,InspectionInfoActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("key",vehicleList[position])
            intent.putExtra("bundle",bundle)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }


}
class PersonInspectionViewHolder(private val binding: PersonInspectionItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setData(bean : BaseInfo_Hand){
        binding.tvHphm.text = bean.hphm
        binding.tvHpzl.text = bean.hpzl
        binding.tvAjywlb.text = "安检:${bean.ajywlb}"
        binding.tvHjjwlb.text = "环保：${bean.hjywlb}"
        binding.tvLsh.text = bean.Lsh
    }

}